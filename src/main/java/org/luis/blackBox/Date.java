package org.luis.blackBox;

/**
 * Class that used to store and validate a date.
 *
 * A data is valid if the year is in the range [1900, 2050], the month is in the range [1,12] and
 * the day is in the range [1,31] but having into account the month and the leap years.
 */
public class Date {
  private final int day ;
  private final int month ;
  private final int year ;

  public int getDay() {
    return day;
  }

  public int getMonth() {
    return month;
  }

  public int getYear() {
    return year;
  }

  public Date(int day, int month, int year) {
    this.day = day ;
    this.month = month ;
    this.year = year ;
  }

  public boolean validate() {
    return (year >= 1900 && year <= 2050) && (month >= 1 && month <= 12) && (day >= 1 && day <= 31) &&
        (day <= daysInMonth(month, year));
  }

  private int daysInMonth(int month, int year) {
    if (month == 2) {
      if (isLeapYear(year)) {
        return 29;
      } else {
        return 28;
      }
    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
      return 30;
    } else {
      return 31;
    }
  }

  private boolean isLeapYear(int year) {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
  }
}



