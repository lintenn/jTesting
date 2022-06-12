package org.luis.blackBox;

/**
 * Class that used to store and validate a string that will be used as a password.
 *
 * A string is a valid password if their length is in the range [8, 20] and it contains at least
 * a number and at least a special character from the set {'.', ',',':','?','¿'}
 */
public class PasswordString {
  private final String password ;

  public String getPassword() {
    return password;
  }

  public PasswordString(String string) {
    this.password = string ;
  }

  public boolean validate() {
    return password != null && password.length() >= 8 && password.length() <= 20 && hasNumber() && hasSpecialChar() ;
  }

  private boolean hasNumber() {
    return password.matches(".*\\d+.*");
  }

  private boolean hasSpecialChar() {
    return password.matches(".*[\\.\\,\\:\\?\\¿].*");
  }
}
