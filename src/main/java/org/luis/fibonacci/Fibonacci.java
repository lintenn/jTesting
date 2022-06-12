package org.luis.fibonacci;

/**
 * Class providing a method that computes the Fibonacci sequence number in
 * a given valid position
 *
 * @author Luis Miguel García Marín
 */

public class Fibonacci {

    /**
     * This method implements the iterative version of the Fibonacci sequence.
     * Negative positions are invalid.
     * @param position on the Fibonacci sequence, starting by 0
     * @return Fibonacci sequence number in the given position
     */

    public int compute(int position) {
        if (position < 0) {
            throw new RuntimeException("The position is negative: " + position);
        }

        int num = 0;
        int numPlusOne = 1;
        int temp;

        for (int i = 0; i < position; i++) {
            temp = num;
            num = numPlusOne;
            numPlusOne = numPlusOne + temp;
        }

        return num;
    }
}