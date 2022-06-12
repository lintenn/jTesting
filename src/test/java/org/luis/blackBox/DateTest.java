package org.luis.blackBox;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DateTest {

    /**
     * Test cases:
     * 0. Test constructor with valid date 1/1/1900
     * Equivalence classes:
     * 1. year < 1900 -> invalid
     * 2. year > 2050 -> invalid
     * 3. month < 1 -> invalid
     * 4. month > 12 -> invalid
     * 5. day < 1 -> invalid
     * 6. day > 31 -> invalid
     * 7. (month == 4 || month == 6 || month == 9 || month == 11) && day > 30 -> invalid
     * 8. (month == 2) && (day > 29) && isLeap(year) -> invalid
     * 9. (month == 2) && (day > 28) && !isLeap(year) -> invalid
     * 10. In other case -> valid (ej. 31/12/2050)
     */

    // 0.
    @Test
    public void testConstructorWithValidDate() {
        Date date = new Date(1, 1, 1900);
        assertThat(date.getDay()).isEqualTo(1);
        assertThat(date.getMonth()).isEqualTo(1);
        assertThat(date.getYear()).isEqualTo(1900);
        assertThat(date.validate()).isTrue();
    }

    // 1.
    @Test
    public void IfYearLessThan1900DateIsInvalid() {
        Date date = new Date(1, 1, 1899);

        assertThat(date.validate()).isFalse();
    }

    // 2.
    @Test
    public void IfYearGreaterThan2050DateIsInvalid() {
        Date date = new Date(1, 1, 2051);

        assertThat(date.validate()).isFalse();
    }

    // 3.
    @Test
    public void IfMonthLessThan1DateIsInvalid() {
        Date date = new Date(1, 0, 1900);

        assertThat(date.validate()).isFalse();
    }

    // 4.
    @Test
    public void IfMonthGreaterThan12DateIsInvalid() {
        Date date = new Date(1, 13, 1900);

        assertThat(date.validate()).isFalse();
    }

    // 5.
    @Test
    public void IfDayLessThan1DateIsInvalid() {
        Date date = new Date(0, 1, 1900);

        assertThat(date.validate()).isFalse();
    }

    // 6.
    @Test
    public void IfDayGreaterThan31DateIsInvalid() {
        Date date = new Date(32, 1, 1900);

        assertThat(date.validate()).isFalse();
    }

    // 7.1.
    @Test
    public void IfMonthIs4AndDayGreaterThan30DateIsInvalid() {
        Date date = new Date(31, 4, 1900);

        assertThat(date.validate()).isFalse();
    }

    // 7.2.
    @Test
    public void IfMonthIs6AndDayGreaterThan30DateIsInvalid() {
        Date date = new Date(31, 6, 1900);

        assertThat(date.validate()).isFalse();
    }

    // 7.3.
    @Test
    public void IfMonthIs9AndDayGreaterThan30DateIsInvalid() {
        Date date = new Date(31, 9, 1900);

        assertThat(date.validate()).isFalse();
    }

    // 7.4.
    @Test
    public void IfMonthIs11AndDayGreaterThan30DateIsInvalid() {
        Date date = new Date(31, 11, 1900);

        assertThat(date.validate()).isFalse();
    }

    // 8.
    @Test
    public void IfMonthIs2AndDayGreaterThan29AndIsLeapYearDateIsInvalid() {
        Date date = new Date(30, 2, 2000);

        assertThat(date.validate()).isFalse();
    }

    // 9.
    @Test
    public void IfMonthIs2AndDayGreaterThan28AndIsNotLeapYearDateIsInvalid() {
        Date date = new Date(29, 2, 1900);

        assertThat(date.validate()).isFalse();
    }

    // 10.1.
    @Test
    public void IfMonthIs2AndDayIs29AndIsLeapYearDateIsValid() {
        Date date = new Date(29, 2, 2000);

        assertThat(date.validate()).isTrue();
    }

    // 10.2.
    @Test
    public void IfMonthIs2AndDayIs28AndIsNotLeapYearDateIsValid() {
        Date date = new Date(28, 2, 1900);

        assertThat(date.validate()).isTrue();
    }

    // 10.3.
    @Test
    public void IfYearIsCorrectAndMonthIsCorrectAndDayIsCorrectDateIsValid() {
        Date date = new Date(31, 12, 2050);

        assertThat(date.validate()).isTrue();
    }

}