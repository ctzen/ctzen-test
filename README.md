# ctzen-test

Test Utilities.

### `MockMvcResultHandlers`

Custom version [spring-test-mvc](http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/testing.html)'s [MockMvcResultHandlers](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/test/web/servlet/result/MockMvcResultHandlers.html) which prints to SLF4J Logger, and TestNG Reporter, in addition to stdout.

e.g.
```java
import static com.ctzen.test.spring.web.servlet.result.MockMvcResultHandlers.*;

mockMvc.perform(get("/foo")
       .andDo(reporter());
       
mockMvc.perform(get("/foo")
       .andDo(log(logger));
```