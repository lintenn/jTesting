package org.luis.factorial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuccessionTest {

    private Succession succession;

    @BeforeEach
    public void setup() {
        succession = new Succession();
    }

    @AfterEach
    public void finish() {
        succession = null;
    }

    @Test
    public void shouldSumReturnZeroIfTheNumberIsZero() {
        int expectedValue = 0;
        int obtainedValue = succession.sumRecursive(0);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldSumReturnOneIfTheNumberIsOne() {
        int expectedValue = 1;
        int obtainedValue = succession.sumRecursive(1);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldSumReturnThreeIfTheNumberIsTwo() {
        int expectedValue = 3;
        int obtainedValue = succession.sumRecursive(2);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldSumReturnFiftyFiveIfTheNumberIsTen() {
        int expectedValue = succession.sum(10); //55
        int obtainedValue = succession.sumRecursive(10);

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldSumReturnABigNumberIfTheNumberIsABigNumber() {
        int expectedValue = succession.sum(5000);
        int obtainedValue = succession.sumRecursive(5000); //100000

        assertEquals(expectedValue, obtainedValue);
    }
}