package com.ctzen.test.spring.web.servlet.result;

import org.slf4j.Logger;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.util.CollectionUtils;
import org.testng.Reporter;

/**
 * Custom {@link org.springframework.test.web.servlet.result.MockMvcResultHandlers}
 *
 * @author cchang
 */
public abstract class MockMvcResultHandlers {

    private MockMvcResultHandlers() {
    }

    /**
     * Print {@link MvcResult} details to the "standard" output stream.
     */
    public static ResultHandler print() {
        return org.springframework.test.web.servlet.result.MockMvcResultHandlers.print();
    }

    /**
     * Print {@link MvcResult} details to a {@link Logger}.
     */
    public static ResultHandler log(final Logger log) {
        return new LogPrintingResultHandler(log);
    }

    /**
     * Print {@link MvcResult} details to TestNG {@link Reporter}.
     */
    public static ResultHandler reporter() {
        return new ReporterPrintingResultHandler();
    }

    private static class LogPrintingResultHandler extends PrintingResultHandler {

        public LogPrintingResultHandler(final Logger log) {
            super(new LogResultValuePrinter());
            this.log = log;
        }

        private final Logger log;

        @Override
        protected void printResponse(final MockHttpServletResponse response) throws Exception {
            super.printResponse(response);
            // sort of a hack because printResponse is the last thing printed
            log.debug(((LogResultValuePrinter)getPrinter()).sb.toString());
        }

        private static class LogResultValuePrinter implements ResultValuePrinter {

            private final StringBuilder sb = new StringBuilder();

            @Override
            public void printHeading(final String heading) {
                sb.append("\n")
                  .append(String.format("%20s:", heading))
                  .append("\n");
            }

            @Override
            public void printValue(final String label, Object value) {
                if (value != null && value.getClass().isArray()) {
                    value = CollectionUtils.arrayToList(value);
                }
                sb.append(String.format("%20s = %s", label, value))
                  .append("\n");
            }
        }

    }

    private static class ReporterPrintingResultHandler extends PrintingResultHandler {

        public ReporterPrintingResultHandler() {
            super(new ReporterResultValuePrinter());
        }

        private static class ReporterResultValuePrinter implements ResultValuePrinter {

            @Override
            public void printHeading(final String heading) {
                Reporter.log(String.format("%20s:", heading));
            }

            @Override
            public void printValue(final String label, Object value) {
                if (value != null && value.getClass().isArray()) {
                    value = CollectionUtils.arrayToList(value);
                }
                Reporter.log(String.format("%20s = %s", label, value));
            }
        }

    }

}
