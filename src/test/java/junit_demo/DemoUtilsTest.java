package junit_demo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

//@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
class DemoUtilsTest {

    private DemoUtils demoUtils;

    @BeforeAll
    static void setupBeforeAllTests() {
        System.out.println("@BeforeAll execution\n");
    }

    @AfterAll
    static void setupAfterAllTests() {
        System.out.println("@BeforeAll execution");
    }

    @BeforeEach
    void setupCreateTestObject() {
        demoUtils = new DemoUtils();
        System.out.println("@BeforeEach execution");
    }

    @AfterEach
    void setupDeleteTestObject() {
        demoUtils = new DemoUtils();
        System.out.println("@AfterEach execution\n");

    }

    @Test
    @DisplayName("Checking for equals / not equals")
    void testAdd() {
//        System.out.println("Running test: testAdd");
        assertEquals(6, demoUtils.add(2, 4));
        assertNotEquals(7, demoUtils.add(2, 4));
    }

    @Test
    @DisplayName("Checking for null / not null")
    void testCheckNull() {
//        System.out.println("Running test: testCheckNull");
        String str1 = null;
        String str2 = "test";
        assertNull(demoUtils.checkNull(str1), "should be null");
        assertNotNull(demoUtils.checkNull(str2), "should not be null");
    }
}
