package de.caritas.cob.statisticsservice.api.authorization;

import static de.caritas.cob.statisticsservice.api.authorization.Authority.CONSULTANT;
import static de.caritas.cob.statisticsservice.api.authorization.Authority.fromRoleName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import de.caritas.cob.statisticsservice.api.authorization.Authority.AuthorityValue;
import org.junit.jupiter.api.Test;

public class AuthorityTest {

  @Test
  public void getAuthority_Should_returnExpectedAuthority_When_authorityIsConsultant() {
    String authority = CONSULTANT.getAuthority();

    assertThat(authority, is(AuthorityValue.CONSULTANT_DEFAULT));
  }

  @Test
  public void fromRoleName_Should_returnNull_When_roleNameIsNull() {
    Authority authority = fromRoleName(null);

    assertThat(authority, nullValue());
  }

  @Test
  public void fromRoleName_Should_returnNull_When_roleNameDoesNotExist() {
    Authority authority = fromRoleName("not existing");

    assertThat(authority, nullValue());
  }

  @Test
  public void fromRoleName_Should_returnConsultant_When_roleNameIsConsultant() {
    Authority authority = fromRoleName("consultant");

    assertThat(authority, is(CONSULTANT));
  }

}
