package org.luis.mocking;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MockingExamplesTest {

  private class BCryptPasswordEncoder {

    private final int passwordMaxLength;

    public BCryptPasswordEncoder(int passwordMaxLength) {
      this.passwordMaxLength = passwordMaxLength;
    }

    private boolean isPasswordTooLong(String rawPassword) {
      return rawPassword.length() > passwordMaxLength;
    }

    public String encodePassword(String rawPassword, String salt) {
      String encodedPassword;

      if (this.isPasswordTooLong(rawPassword)) {
        throw new RuntimeException("Invalid password. It is too long.");
      }
      // ...
      // Here would be the encoder algorithm. For the simplicity of this example, we are going to return the rawPassword + salt.
      // ...
      encodedPassword = rawPassword + salt;

      return encodedPassword;
    }

  }

  private class User {
    private String username;
    private String email;
    private String password;
    private Date birthDate;

    public User() {
      super(); // constructor for stub
    }

    public User(String name, String email, String password, Date birthDate) {
      this.username = name;
      this.email = email;
      this.password = password;
      this.birthDate = birthDate;
    }

    public String getUsername() {
      return username;
    }

    public String getEmail() {
      return email;
    }

    public String getPassword() {
      return password;
    }

    public Date getBirthDate() {
      return birthDate;
    }

    public void setUsername(String name) {
      this.username = name;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public void setBirthDate(Date birthDate) {
      this.birthDate = birthDate;
    }
  }

  private interface UserDatabase {
    void save(User user);
    List<User> findAll();
  }

  private class UserService {
    private UserDatabase userDatabase;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserDatabase userDatabase, BCryptPasswordEncoder passwordEncoder) {
      this.userDatabase = userDatabase;
      this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User newUser) {
      // For security, we need to encode the password before saving it in the database.
      newUser.setPassword(passwordEncoder.encodePassword(newUser.getPassword(), "salt123"));

      userDatabase.save(newUser);
    }

    public int getNumberOfUsers() {
      return userDatabase.findAll().size();
    }

    public List<User> getUsersWithSpecifiedBirthDate(Date birthDate) {
      List<User> users = userDatabase.findAll();

      List<User> usersWithSpecifiedBirthDate = new ArrayList<>();

      for (User user : users) {
        if (user.getBirthDate().equals(birthDate)) {
          usersWithSpecifiedBirthDate.add(user);
        }
      }

      return usersWithSpecifiedBirthDate;
    }
  }

  public class FakeUserDatabase implements UserDatabase {
    // To simulate the database, we use a List.
    private List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
      users.add(user);
    }

    @Override
    public List<User> findAll() {
      return users;
    }
  }

  /**
   * EXAMPLE FOR DUMMIE (the salt in this case):
   * In this example, the SUT (System Under Test) is BCryptPasswordEncoder.
   * We need to pass some salt as an argument for the function "encodePassword" and we do not care about what it is,
   * because we are going to test that the function raises an exception for a given too long raw password and
   * it will not be used. So we can use a dummy salt.
   */
  @Test
  public void shouldEncodePasswordRaiseExceptionIfTheInputPasswordIsTooLong() {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(32);

    String rawPassword = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    String salt = "dummy salt that will not be used";

    assertThrows(RuntimeException.class, () -> encoder.encodePassword(rawPassword, salt));
  }

  /**
   * EXAMPLE FOR FAKE (FakeUserDatabase):
   * In this example, the SUT is UserService.
   * We need to pass a UserDatabase as an argument for the constructor of UserService.
   * We can use a FakeUserDatabase to simulate the database with the minimum implementation we need.
   */
  @Test
  public void shouldRegisterAddNewUsersToDataBase() {
    // Arrange - create a fake database and a service that uses it.
    // We need to create a fake database because we want to test the service.
    // We could also consider the BCryptPasswordEncoder as a fake, because it only implements the logic that we need for this test. It has not the real algorithmic encoder implementation.
    UserDatabase userDatabase = new FakeUserDatabase();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(32);
    UserService userService = new UserService(userDatabase, passwordEncoder);

    // Act - register new users
    userService.registerUser(new User("Luis", "luis@gmail.com", "12345678", new Date(2000, Calendar.FEBRUARY, 1)));
    userService.registerUser(new User("Pedro", "pedro@gmail.com", "12345678", new Date(2001, Calendar.FEBRUARY, 22)));

    // Assert - check the number of users in the database
    assertEquals(2, userService.getNumberOfUsers());
  }

  /**
   * EXAMPLE OF A STUB (using Mockito):
   * In this example, the SUT is UserService.
   * We need to pass a UserDatabase as an argument for the constructor of UserService.
   * We can use a stub to simulate the database.
   */
  @Test
  public void shouldGetUsersWithSpecifiedBirthDateReturnTheUsersInStubDatabaseIfTheInputDateIsCorrect() {
    // Arrange - create a stub for the UserDatabase
    // We need to create a stub database because we want to test the service.
    // We could also consider the BCryptPasswordEncoder as a fake, because it only implements the logic that we need for this test. It has not the real algorithmic encoder implementation.
    UserDatabase stubUserDatabase = mock(UserDatabase.class);
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(32);
    UserService userService = new UserService(stubUserDatabase, passwordEncoder);

    // Stubbing - we want to simulate the behavior of the database.
    List<User> users = new ArrayList<>();
    users.add(new User("Luis", "luis@gmail.com", "12345678salt123", new Date(2000, Calendar.FEBRUARY, 1)));
    users.add(new User("Pedro", "pedro@gmail.com", "12345678salt123", new Date(2001, Calendar.FEBRUARY, 22)));
    when(stubUserDatabase.findAll()).thenReturn(users);


    // Act - get users with specified birth date
    List<User> usersWithSpecifiedBirthDate = userService.getUsersWithSpecifiedBirthDate(new Date(2000, Calendar.FEBRUARY, 1));

    // Assert - check the number of users in the result
    assertEquals(1, usersWithSpecifiedBirthDate.size());
    assertEquals("Luis", usersWithSpecifiedBirthDate.get(0).getUsername());
  }

  /**
   * EXAMPLE OF A SPY (using Mockito):
   * In this example, the SUT is UserService.
   * We need to pass a UserDatabase as an argument for the constructor of UserService.
   * We can use a spy to verify information of the spied implementation of the database.
   * For the simplicity of this example, we will not use the real implementation of database for the spy.
   * We are going to reuse the fake database for the spy, but in a real case we should use the real implementation.
   */
  @Test
  public void shouldRegisterAddNewUsersToSpyDataBase() {
    // Arrange - create a spy for the UserDatabase
    // We need to create a spy because we want to test the service.
    // We could also consider the BCryptPasswordEncoder as a fake, because it only implements the logic that we need for this test. It has not the real algorithmic encoder implementation.
    UserDatabase spyUserDatabase = spy(new FakeUserDatabase());
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(32);
    UserService userService = new UserService(spyUserDatabase, passwordEncoder);

    // Act - register new users
    userService.registerUser(new User("Luis", "luis@gmail.com", "12345678", new Date(2000, Calendar.FEBRUARY, 1)));
    userService.registerUser(new User("Pedro", "pedro@gmail.com", "12345678", new Date(2001, Calendar.FEBRUARY, 22)));

    // Assert - check the number of users in the database
    assertEquals(2, userService.getNumberOfUsers());
    verify(spyUserDatabase, times(1)).findAll();
    verify(spyUserDatabase, times(2)).save(any(User.class));
  }

  /**
   * EXAMPLE OF A MOCK (using Mockito):
   * In this example, the SUT is UserService.
   * We need to pass a UserDatabase as an argument for the constructor of UserService.
   * We can use a mock to simulate the behavior of the database and verify behaviours and information.
   */
  @Test
  public void shouldGetUsersWithSpecifiedBirthDateReturnTheUsersInMockDatabaseIfTheInputDateIsCorrect() {
    // Arrange - create a mock for the UserDatabase
    // We need to create a mock database because we want to test the service.
    // We could also consider the BCryptPasswordEncoder as a fake, because it only implements the logic that we need for this test. It has not the real algorithmic encoder implementation.
    UserDatabase mockUserDatabase = mock(UserDatabase.class);
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(32);
    UserService userService = new UserService(mockUserDatabase, passwordEncoder);

    // Stubbing - we want to simulate the behavior of the database.
    List<User> users = new ArrayList<>();
    users.add(new User("Luis", "luis@gmail.com", "12345678salt123", new Date(2000, Calendar.FEBRUARY, 1)));
    users.add(new User("Pedro", "pedro@gmail.com", "12345678salt123", new Date(2001, Calendar.FEBRUARY, 22)));
    when(mockUserDatabase.findAll()).thenReturn(users);


    // Act - get users with specified birth date
    List<User> usersWithSpecifiedBirthDate = userService.getUsersWithSpecifiedBirthDate(new Date(2000, Calendar.FEBRUARY, 1));

    // Assert - check the number of users in the result
    assertEquals(1, usersWithSpecifiedBirthDate.size());
    assertEquals("Luis", usersWithSpecifiedBirthDate.get(0).getUsername());
    verify(mockUserDatabase, times(1)).findAll();
    verify(mockUserDatabase, never()).save(any(User.class));
  }



}
