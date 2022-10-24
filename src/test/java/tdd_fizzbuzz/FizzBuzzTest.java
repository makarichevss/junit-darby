package tdd_fizzbuzz;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {

    @Test
    @Order(1)
    @DisplayName("Divisible by 3 and 5")
    void testDivBy3and5() {
        assertEquals("FizzBuzz", FizzBuzz.compute(15));
    }

    @Test
    @Order(2)
    @DisplayName("Divisible by 5")
    void testDivBy5() {
        assertEquals("Buzz", FizzBuzz.compute(5));
    }

    @Test
    @Order(3)
    @DisplayName("Divisible by 3")
    void testDivBy3() {
        assertEquals("Fizz", FizzBuzz.compute(3));
    }

    @Test
    @Order(4)
    @DisplayName("Not divisible by 3 or 5")
    void testNotDivBy3or5() {
        assertEquals("2", FizzBuzz.compute(2));
    }

    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/medium-test-data.csv")
    @Order(0)
    @DisplayName("Params for FizzBuzz")
    void testParamFizzBuzz(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute(value));
    }
}
