package de.caritas.cob.statisticsservice.api.helper;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthenticatedUserTest {

  @Test
  public void AuthenticatedUser_Should_ThrowNullPointerExceptionWhenArgumentsAreNull() {
    assertThrows(NullPointerException.class, () -> {
      new AuthenticatedUser(null, null, null);
    });
  }

  @Test
  public void AuthenticatedUser_Should_ThrowNullPointerExceptionWhenUserIdIsNull() {
    assertThrows(NullPointerException.class, () -> {
      AuthenticatedUser authenticatedUser = new AuthenticatedUser();
      authenticatedUser.setUserId(null);
    });
  }

  @Test
  public void AuthenticatedUser_Should_ThrowNullPointerExceptionWhenUsernameIsNull() {
    assertThrows(NullPointerException.class, () -> {
      AuthenticatedUser authenticatedUser = new AuthenticatedUser();
      authenticatedUser.setUsername(null);
    });
  }
}
