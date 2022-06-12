package org.luis.decisionConditionCoverage;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * @version 1.0
 */
public class LexicographicalVectorComparatorTest {

  private LexicographicalVectorComparator comparator;

  @BeforeEach
  public void startup() {
    comparator = new LexicographicalVectorComparator();
  }

  @Test
  public void shouldFirstPointToCompareEqualsToNullRaiseAnException() {
    assertThrows(NullPointerException.class, () -> comparator.compare(null, new double[]{1, 2}));
  }

  @Test
  public void shouldSecondPointToCompareEqualsToNullRaiseAnException() {
    assertThrows(NullPointerException.class, () -> comparator.compare(new double[]{1, 2}, null));
  }

  @Test
  public void shouldCompareIdenticalPointsReturnZero() {
    assertEquals(0, comparator.compare(new double[]{1, 3}, new double[]{1, 3}));
  }

  @Test
  public void shouldCompareIdenticalPointsButTheFirstValueReturnPlus1() {
    double[] point1 = new double[]{1.0, 0.0, 5.0, 7.0};
    double[] point2 = new double[]{-1.0, 0.0, 5.0, 7.0};

    assertEquals(1, comparator.compare(point1, point2));
  }

  @Test
  public void shouldCompareIdenticalPointsButTheFirstValueReturnMinus1() {
    double[] point1 = new double[]{-1.0, 0.0, 5.0, 7.0};
    double[] point2 = new double[]{1.0, 0.0, 5.0, 7.0};

    assertEquals(-1, comparator.compare(point1, point2));
  }

  @Test
  public void shouldCompareIdenticalPointsButTheLastValueReturnMinus1() {
    double[] point1 = new double[]{1.0, 0.0, 5.0, 0.0};
    double[] point2 = new double[]{1.0, 0.0, 5.0, 7.0};

    assertEquals(-1, comparator.compare(point1, point2));
  }

  @Test
  public void shouldCompareIdenticalPointsButTheLastValueReturnPlus1() {
    double[] point1 = new double[]{1.0, 0.0, 5.0, 7.0};
    double[] point2 = new double[]{1.0, 0.0, 5.0, 0.0};

    assertEquals(1, comparator.compare(point1, point2));
  }

  @Test
  public void shouldCompareEmptyPointsReturnZero() {
    assertEquals(0, comparator.compare(new double[]{}, new double[]{}));
  }

  @Test
  public void shouldCompareDifferentLengthPointsWithTheSameValuesInTheCommonPositionsReturnZero() {
    double[] point1 = new double[]{1.0, 0.0, 5.0, 7.0};
    double[] point2 = new double[]{1.0, 0.0, 5.0};

    assertEquals(0, comparator.compare(point1, point2));
  }

  /**
   * Tests de Cobertura de decisiones (bucle while):
   * compare [] [] -> 0 // no pasa por el bucle *
   * compare [1] [2] -> -1 // no pasa por el bucle *
   *
   * compare [1] [1] -> 0 // pasa 0 vez por el bucle *
   * compare [1,2,3] [1,2,4] -> -1 // pasa 2 veces por el bucle *
   *
   * compare [1,2,3] [1,2,3] -> 0 // pasa 3 veces por el bucle *
   * compare [1,2,3] [1,2,3,4,5] -> 0 // pasa 3 veces por el bucle *
   * compare [1,2,3,4,5] [1,2,3,4] -> 0 // pasa 4 veces por el bucle
   */
  @Test
  public void shouldPassWhile0TimesWithEmptyPoints() {
    double[] point1 = new double[]{};
    double[] point2 = new double[]{};

    assertEquals(0, comparator.compare(point1, point2) );
  }

  @Test
  public void shouldPassWhile0TimesWithSameLengthButDifferentPoints() {
    double[] point1 = new double[]{1.0};
    double[] point2 = new double[]{2.0};

    assertEquals(-1, comparator.compare(point1, point2) );
  }

  @Test
  public void shouldPassWhile1TimesWithSameLengthAndSamePoints() {
    double[] point1 = new double[]{1.0};
    double[] point2 = new double[]{1.0};

    assertEquals(0, comparator.compare(point1, point2) );
  }

  @Test
  public void shouldPassWhile2TimesWithSameLengthAndDifferentLastPoint() {
    double[] point1 = new double[]{1.0, 2.0, 3.0};
    double[] point2 = new double[]{1.0, 2.0, 4.0};

    assertEquals(-1, comparator.compare(point1, point2) );
  }

  @Test
  public void shouldPassWhile3TimesWithSameLengthAndSamePoints() {
    double[] point1 = new double[]{1.0, 2.0, 3.0};
    double[] point2 = new double[]{1.0, 2.0, 3.0};

    assertEquals(0, comparator.compare(point1, point2) );
  }

  @Test
  public void shouldPassWhile3TimesWithFirstPointLengthLowerThanSecondPointLength() {
    double[] point1 = new double[]{1.0, 2.0, 3.0};
    double[] point2 = new double[]{1.0, 2.0, 3.0, 4.0, 5.0};

    assertEquals(0, comparator.compare(point1, point2) );
  }

  @Test
  public void shouldPassWhile4TimesWithFirstPointLengthGreaterThanSecondPointLength() {
    double[] point1 = new double[]{1.0, 2.0, 3.0, 4.0, 5.0};
    double[] point2 = new double[]{1.0, 2.0, 3.0};

    assertEquals(0, comparator.compare(point1, point2) );
  }

  /**
   * Tests de Cobertura de condiciones (if):
   * compare [] [] -> 0 // (1ª condición == true) OR (2ª condición == true) en el if
   * compare [1] [2] -> -1 // (1ª condición == false) OR (2ª condición == false) en el if
   * compare [1,2] [1,2,3] -> 0 // (1ª condición == true) OR (2ª condición == false) en el if
   * compare [1,2,3] [1,2] -> 0 // (1ª condición == false) OR (2ª condición == true) en el if
   */

  @Test
  public void shouldPassIfWithFirstConditionTrueORSecondConditionTrueWithEmptyPoints() {
    double[] point1 = new double[]{};
    double[] point2 = new double[]{};

    assertEquals(0, comparator.compare(point1, point2) );
  }

  @Test
  public void shouldNotPassIfWithFirstConditionFalseORSecondConditionFalseWithSameLengthButDifferentPoints() {
    double[] point1 = new double[]{1.0};
    double[] point2 = new double[]{2.0};

    assertEquals(-1, comparator.compare(point1, point2) );
  }

  @Test
  public void shouldPassIfWithFirstConditionTrueORSecondConditionFalseWithFirstPointLengthLowerThanSecondPointLength() {
    double[] point1 = new double[]{1.0, 2.0};
    double[] point2 = new double[]{1.0, 2.0, 3.0};

    assertEquals(0, comparator.compare(point1, point2) );
  }

  @Test
  public void shouldPassIfWithFirstConditionFalseORSecondConditionTrueWithFirstPointLengthGreaterThanSecondPointLength() {
    double[] point1 = new double[]{1.0, 2.0, 3.0};
    double[] point2 = new double[]{1.0, 2.0};

    assertEquals(0, comparator.compare(point1, point2) );
  }
}
