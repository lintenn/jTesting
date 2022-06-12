package org.luis.triangle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases:
 * getType 3 3 4 -> ISOSCELES *
 * getType 4 3 3 -> ISOSCELES *
 * getType 3 4 3 -> ISOSCELES *
 * getType 2 3 4 -> SCALENE *
 * getType 2.5 3.5 5.5 -> SCALENE *
 * getType 4 4 4 -> EQUILATERAL *
 * getType 1 2 3 -> Exception (invalid triangle) *
 * getType 1 3 2 -> Exception (invalid triangle) *
 * getType 3 1 2 -> Exception (invalid triangle) *
 * getType 2 10 5 -> Exception (invalid triangle) *
 * getType 2 5 10 -> Exception (invalid triangle) *
 * getType 10 2 5 -> Exception (invalid triangle) *
 * getType 0 0 0 -> Exception (zero values) *
 * getType 3 3 0 -> Exception (zero value) *
 * getType -1 3 3 -> Exception (negative value) *
 */

class TriangleTest {

    private Triangle triangle;

    @BeforeEach
    public void setup() {
        triangle = new Triangle();
    }

    @AfterEach
    public void finish() {
        triangle = null;
    }

    @Test
    public void shouldGetTypeReturnISOSCELESIfTheLengthsAreThreeThreeFour() {
        Triangle.TriangleType expectedValue = Triangle.TriangleType.ISOSCELES;
        Triangle.TriangleType obtainedValue = triangle.getType(3,3,4);
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldGetTypeReturnISOSCELESIfTheLengthsAreFourThreeThree() {
        Triangle.TriangleType expectedValue = Triangle.TriangleType.ISOSCELES;
        Triangle.TriangleType obtainedValue = triangle.getType(4,3,3);
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldGetTypeReturnISOSCELESIfTheLengthsAreThreeFourThree() {
        Triangle.TriangleType expectedValue = Triangle.TriangleType.ISOSCELES;
        Triangle.TriangleType obtainedValue = triangle.getType(3,4,3);
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldGetTypeReturnSCALENEIfTheLengthsAreTwoThreeFour() {
        Triangle.TriangleType expectedValue = Triangle.TriangleType.SCALENE;
        Triangle.TriangleType obtainedValue = triangle.getType(2,3,4);
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldGetTypeReturnSCALENEIfTheLengthsAreTwoPointFiveThreePointFiveFivePointFive() {
        Triangle.TriangleType expectedValue = Triangle.TriangleType.SCALENE;
        Triangle.TriangleType obtainedValue = triangle.getType(2.5,3.5,5.5);
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldGetTypeReturnEQUILATERALIfTheLengthsAreFourFourFour() {
        Triangle.TriangleType expectedValue = Triangle.TriangleType.EQUILATERAL;
        Triangle.TriangleType obtainedValue = triangle.getType(4,4,4);
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    public void shouldGetTypeRaiseAnExceptionIfTheLengthsAreOneTwoThree() {
        assertThrows(RuntimeException.class, () -> triangle.getType(1,2,3));
    }

    @Test
    public void shouldGetTypeRaiseAnExceptionIfTheLengthsAreOneThreeTwo() {
        assertThrows(RuntimeException.class, () -> triangle.getType(1,3,2));
    }

    @Test
    public void shouldGetTypeRaiseAnExceptionIfTheLengthsAreThreeOneTwo() {
        assertThrows(RuntimeException.class, () -> triangle.getType(3,1,2));
    }

    @Test
    public void shouldGetTypeRaiseAnExceptionIfTheLengthsAreTwoTenFive() {
        assertThrows(RuntimeException.class, () -> triangle.getType(2,10,5));
    }

    @Test
    public void shouldGetTypeRaiseAnExceptionIfTheLengthsAreTwoFiveTen() {
        assertThrows(RuntimeException.class, () -> triangle.getType(2,5,10));
    }

    @Test
    public void shouldGetTypeRaiseAnExceptionIfTheLengthsAreTenTwoFive() {
        assertThrows(RuntimeException.class, () -> triangle.getType(10,2,5));
    }

    @Test
    public void shouldGetTypeRaiseAnExceptionIfTheLengthsAreZeroZeroZero() {
        assertThrows(RuntimeException.class, () -> triangle.getType(0,0,0));
    }

    @Test
    public void shouldGetTypeRaiseAnExceptionIfTheLengthsAreThreeThreeZero() {
        assertThrows(RuntimeException.class, () -> triangle.getType(3,3,0));
    }

    @Test
    public void shouldGetTypeRaiseAnExceptionIfTheLengthsAreNegativeOneThreeThree() {
        assertThrows(RuntimeException.class, () -> triangle.getType(-1,3,3));
    }

}