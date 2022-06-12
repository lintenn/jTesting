package org.luis.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.luis.authentication.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Integration test for the {@link CredentialValidator} class.
 * @authors Luis Miguel García Marín, Galo Pérez Gallego, Angelo Gorgone Carvajal
 */

public class IntegrationTestIT {

    private UserRegistration userRegistration;

    @BeforeEach
    void setUp() {

        userRegistration = new UserRegistration();
    }


    @Test
    void registerShouldRegisterUserWithValidCredentialsWithStubs() {

        CredentialStore credentialStore = mock(CredentialStore.class);
        Date birtDate = mock(Date.class);
        PasswordString passwordString = mock(PasswordString.class);

        //Stubbing
        when(credentialStore.credentialExists(birtDate, passwordString)).thenReturn(true);
        when(birtDate.validate()).thenReturn(true);
        when(passwordString.validate()).thenReturn(true);

        userRegistration.register(birtDate, passwordString, credentialStore);

        //Assertions
        assertThat(credentialStore.credentialExists(birtDate, passwordString)).isTrue();
    }

    @Test
    void registerShouldRegisterUserWithValidCredentialsWithoutCredentialStoreStub(){

        CredentialStore credentialStore = new CredentialStoreSet();
        Date birtDate = mock(Date.class);
        PasswordString passwordString = mock(PasswordString.class);

        //Stubbing
        when(birtDate.validate()).thenReturn(true);
        when(passwordString.validate()).thenReturn(true);

        userRegistration.register(birtDate, passwordString, credentialStore);

        //Assertions
        assertThat(credentialStore.credentialExists(birtDate, passwordString)).isTrue();

    }

    @Test
    void registerShouldRegisterUserWithValidCredentialsWithoutCredentialStoreAndDateStubs(){

        CredentialStore credentialStore = new CredentialStoreSet();
        Date birtDate = new Date(10,1,2001);
        PasswordString passwordString = mock(PasswordString.class);

        //Stubbing
        when(passwordString.validate()).thenReturn(true);

        userRegistration.register(birtDate, passwordString, credentialStore);

        //Assertions
        assertThat(credentialStore.credentialExists(birtDate, passwordString)).isTrue();
    }

    @Test
    void registerShouldRegisterUserWithValidCredentialsWithoutStubs(){

        CredentialStore credentialStore = new CredentialStoreSet();
        Date birtDate = new Date(10,1,2001);
        PasswordString passwordString = new PasswordString("password1.");

        userRegistration.register(birtDate, passwordString, credentialStore);

        //Assertions
        assertThat(credentialStore.credentialExists(birtDate, passwordString)).isTrue();
    }


}
