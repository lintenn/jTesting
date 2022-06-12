package org.luis.decisionConditionCoverage;

import java.util.Comparator;

/**
 * This class implements the Comparator interface for comparing two vectors.
 * The order used is lexicographical numerical order.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * @modified by Luis M. García
 */
public class LexicographicalVectorComparator implements Comparator<double[]> {

    private void checkNotNull(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
    }

    /**
     * The compare method compare the objects o1 and o2.
     *
     * @param x Vector (array) of double values
     * @param y Vector (array) of double values
     * @return The following value: -1 if x < y, 1 if x > y or 0 otherwise.
     */
    @Override
    public int compare(double[] x, double[] y) {
        checkNotNull(x);
        checkNotNull(y);

        // Find the first i such as x[i] != y[i];
        int index = 0;
        while ((index < x.length)
                && (index < y.length)
                && x[index] == y[index]) {
            index++;
        }

        int result;
        if ((index >= x.length) || (index >= y.length)) {
            result = 0 ;
        } else
            result = Double.compare(x[index], y[index]);
        return result ;
    }
}