package org.luis.authentication;

import org.luis.authentication.CredentialValidator.ValidationStatus;

public class UserRegistration {

  public void register(Date birthDate, PasswordString passwordString,
      CredentialStore credentialStore) {

    var credentialValidator = new CredentialValidator(birthDate, passwordString, credentialStore);
    ValidationStatus status = credentialValidator.validate();

    if (status == ValidationStatus.VALIDATION_OK) {
      credentialStore.register(birthDate, passwordString);
    }
  }
}
