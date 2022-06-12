package org.luis.fibonacci;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases:
 *  fibonacci 0 -> 0
 *  fibonacci 1 -> 1
 *  fibonacci 2 -> 1
 *  fibonacci 3 -> 2
 *  fibonacci 4 -> 3
 *  fibonacci 10 -> 55
 *  fibonacci -1 -> Exception
 */

class FibonacciTest {

    private Fibonacci fibonacci;

    @BeforeEach
    public void setup() {
        fibonacci = new Fibonacci();
    }

    @AfterEach
    public void finish() {
        fibonacci = null;
    }

    @Test
    public void shouldComputeReturnZeroIfThePositionIsZero() {
        int expectedValue = 0;
        int obtainedValue = fibonacci.compute(0);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldComputeReturnOneIfThePositionIsOne() {
        int expectedValue = 1;
        int obtainedValue = fibonacci.compute(1);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldComputeReturnOneIfThePositionIsTwo() {
        int expectedValue = 1;
        int obtainedValue = fibonacci.compute(2);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldComputeReturnTwoIfThePositionIsThree() {
        int expectedValue = 2;
        int obtainedValue = fibonacci.compute(3);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldComputeReturnThreeIfThePositionIsFour() {
        int expectedValue = 3;
        int obtainedValue = fibonacci.compute(4);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldComputeReturnFiftyFiveIfThePositionIsTen() {
        int expectedValue = 55;
        int obtainedValue = fibonacci.compute(10);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldComputeOfANegativeNumberRaiseAnException() {
        assertThrows(RuntimeException.class, () -> fibonacci.compute(-1));
    }


}