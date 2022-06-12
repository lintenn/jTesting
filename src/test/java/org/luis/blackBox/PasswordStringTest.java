package org.luis.blackBox;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PasswordStringTest {

    /**
     * Test cases:
     * 0. Test constructor with valid password "abcdefgh1."
     * Equivalence classes:
     * 1. Password is null -> invalid
     * 2. Password's length is less than 8 -> invalid
     * 3. Password's length is greater than 20 -> invalid
     * 4. Password does not contain at least one number -> invalid
     * 5. Password does not contain at least one special character from the set {'.', ',',':','?','¿'} -> invalid
     * 6. In other case -> valid
     */

    // 0.
    @Test
    public void testConstructorWithValidPassword() {
        PasswordString passwordString = new PasswordString("abcdefgh1.");
        assertThat(passwordString.getPassword()).isEqualTo("abcdefgh1.");
        assertThat(passwordString.validate()).isTrue();
    }

    // 1.
    @Test
    public void IfPasswordIsNullItIsInvalid() {
        PasswordString passwordString = new PasswordString(null);
        assertThat(passwordString.validate()).isFalse();
    }

    // 2.
    @Test
    public void IfPasswordIsLessThan8CharactersItIsInvalid() {
        PasswordString passwordString = new PasswordString("abcdefg");
        assertThat(passwordString.validate()).isFalse();
    }

    // 3.
    @Test
    public void IfPasswordIsGreaterThan20CharactersItIsInvalid() {
        PasswordString passwordString = new PasswordString("abcdefghijklmnopqrstuvwxyz");
        assertThat(passwordString.validate()).isFalse();
    }

    // 4.
    @Test
    public void IfPasswordDoesNotContainAtLeastOneNumberItIsInvalid() {
        PasswordString passwordString = new PasswordString("abcdefgh");
        assertThat(passwordString.validate()).isFalse();
    }

    // 5.
    @Test
    public void IfPasswordDoesNotContainAtLeastOneSpecialCharacterFromTheSetItIsInvalid() {
        PasswordString passwordString = new PasswordString("abcdefgh1");
        assertThat(passwordString.validate()).isFalse();
    }

    // 6.
    @Test
    public void IfPasswordIsValidItIsValid() {
        PasswordString passwordString = new PasswordString("abcdefgh2?¿");
        assertThat(passwordString.validate()).isTrue();
    }
}


