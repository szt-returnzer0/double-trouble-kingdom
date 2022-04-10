import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExampleTest {
    @Test
    @DisplayName("Hello")
    void helloTest() {
        assertEquals("Hello World!", "Hello World!", "String should be Hello World!");
    }
}
