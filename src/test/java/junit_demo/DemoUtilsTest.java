package junit_demo;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Equals / not equals")
    void testAdd() {
//        System.out.println("Running test: testAdd");
        assertEquals(6, demoUtils.add(2, 4));
        assertNotEquals(7, demoUtils.add(2, 4));
    }

    @Test
    @DisplayName("is null / not null")
    void testCheckNull() {
//        System.out.println("Running test: testCheckNull");
        String str1 = null;
        String str2 = "test";
        assertNull(demoUtils.checkNull(str1), "should be null");
        assertNotNull(demoUtils.checkNull(str2), "should not be null");
    }

    @Test
    @DisplayName("Is same / not same")
    void testSameWord() {
        String str = "Luv2Code";
        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate());
        assertNotSame(str, demoUtils.getAcademyDuplicate());
    }

    @Test
    @DisplayName("Is bigger value")
    void testTrueBig() {
        int grade1 = 10;
        int grade2 = 5;
        assertTrue(demoUtils.isGreater(grade1, grade2), "Should return true");
        assertFalse(demoUtils.isGreater(grade2, grade1), "Should return false");
    }

    @Test
    @DisplayName("Arrays/Iterables/Strings equality")
    void testStructuresEquality() {
        String[] arr = {"A", "B", "C"};
        List<String> str = List.of("luv", "2", "code");
        assertArrayEquals(arr, demoUtils.getFirstThreeLettersOfAlphabet());
        assertIterableEquals(str, demoUtils.getAcademyInList());
        assertLinesMatch(str, demoUtils.getAcademyInList());
    }
}
