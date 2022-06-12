package org.luis.factorial;

/**
 * Class providing a method that computes the factorial of
 * an integer number
 *
 * @author Luis Miguel García Marín
 */

public class Factorial {
    public int compute(int value) {
        int result;

        if (value < 0) {
            throw new RuntimeException("The value is negative: " + value);
        }

        result = 1;
        for (int i = 2; i <= value; i++) {
            result*=i;
        }

        return result;
    }

    public int computeRecursive(int value) {
        int result;

        if (value < 0) {
            throw new RuntimeException("The value is negative: " + value);
        }

        if (value == 0) {
            result = 1;
        } else {
            result = value * compute(value-1);
        }

        return result;
    }
}
