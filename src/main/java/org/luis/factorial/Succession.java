package org.luis.factorial;

public class Succession {

    private int sumRecursiveRec(int value) {
        int result;

        if (value == 0) {
            result = 0;
        } else {
            result = value + sumRecursiveRec(value-1);
        }

        return result;
    }

    public int sumRecursive(int value) {
        int result=0;
        int limit = 4000;

        if (value < 0) {
            throw new RuntimeException("The value is negative: " + value);
        }

        for (int i = 0; i < value; i += limit) {

        }

        result = sumRecursiveRec(value);

        return result;
    }

    public int sum(int value) {
        if (value < 0) {
            throw new RuntimeException("The value is negative: " + value);
        }

        return value*(value+1)/2;
    }

}
