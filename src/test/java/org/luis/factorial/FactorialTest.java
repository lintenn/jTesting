package org.luis.factorial;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases:
 *  factorial 0 -> 1 *
 *  factorial 1 -> 1 *
 *  factorial 2 -> 2 *
 *  factorial 3 -> 6 *
 *  factorial 6 -> 720 *
 *  factorial -1 -> Excepción
 *  (Disabled)
 *  timeInSeconds (factorial 100) <= 10 -> True
 */

class FactorialTest {

    private Factorial factorial;

    @BeforeEach
    public void setup() {
        factorial = new Factorial();
    }

    @AfterEach
    public void finish() {
        factorial = null;
    }

    @Test
    public void shouldComputeReturnOneIfTheNumberIsZero() {
        //var factorial = new Factorial();
        int expectedValue = 1;
        int obtainedValue = factorial.compute(0);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldComputeReturnOneIfTheNumberIsOne() {
        //var factorial = new Factorial();
        int expectedValue = 1;
        int obtainedValue = factorial.compute(1);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldComputeReturnTwoIfTheNumberIsTwo() {
        //var factorial = new Factorial();
        int expectedValue = 2;
        int obtainedValue = factorial.compute(2);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldComputeReturnSixIfTheNumberIsThree() {
        //var factorial = new Factorial();
        int expectedValue = 6;
        int obtainedValue = factorial.compute(3);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldComputeReturn720IfTheNumberIsSix() {
        //var factorial = new Factorial();
        int expectedValue = 720;
        int obtainedValue = factorial.compute(6);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldComputeOfANegativeNumberRaiseAnException() {
        //var factorial = new Factorial();
        assertThrows(RuntimeException.class, () -> factorial.compute(-1));
    }

    @Disabled
    public void shouldTestSomethingInTheFuture() {
        int expectedValue;
        int obtainedValue;
    }

    @Test
    public void shouldComputeCalculateValueInLessThan10Seconds() {

        assertTimeout(Duration.ofSeconds(10), () -> factorial.compute(100));
    }
}