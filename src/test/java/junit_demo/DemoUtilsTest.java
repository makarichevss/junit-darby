package junit_demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DemoUtilsTest {


    private DemoUtils demoUtils = new DemoUtils();



    @Test
    void testAdd() {
        assertEquals(6, demoUtils.add(2, 4));
        assertNotEquals(7, demoUtils.add(2, 4));
    }

    @Test
    void testCheckNull() {
        DemoUtils demoUtils = new DemoUtils();
        String str1 = null;
        String str2 = "test";
        assertNull(demoUtils.checkNull(str1), "should be null");
        assertNotNull(demoUtils.checkNull(str2), "should not be null");
    }
}
