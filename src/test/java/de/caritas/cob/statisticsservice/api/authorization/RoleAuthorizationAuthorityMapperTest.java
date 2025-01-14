package de.caritas.cob.statisticsservice.api.authorization;

import static de.caritas.cob.statisticsservice.api.authorization.Authority.CONSULTANT;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class RoleAuthorizationAuthorityMapperTest {

  private final RoleAuthorizationAuthorityMapper roleAuthorizationAuthorityMapper =
      new RoleAuthorizationAuthorityMapper();

  @Test
  public void mapAuthorities_Should_returnGrantedConsultantAuthority_When_authorityIsConsultant() {
    List<GrantedAuthority> grantedAuthorities = Stream.of("consultant")
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    Collection<? extends GrantedAuthority> mappedAuthorities = this.roleAuthorizationAuthorityMapper
        .mapAuthorities(grantedAuthorities);

    assertThat(mappedAuthorities, hasSize(1));
    assertThat(mappedAuthorities.iterator().next().getAuthority(), is(CONSULTANT.getAuthority()));
  }

  @Test
  public void mapAuthorities_Should_returnGrantedConsultantAuthority_When_authoritiesContainsConsultant() {
    List<GrantedAuthority> grantedAuthorities = Stream.of("a", "v", "consultant", "c")
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    Collection<? extends GrantedAuthority> mappedAuthorities = this.roleAuthorizationAuthorityMapper
        .mapAuthorities(grantedAuthorities);

    assertThat(mappedAuthorities, hasSize(1));
    assertThat(mappedAuthorities.iterator().next().getAuthority(), is(CONSULTANT.getAuthority()));
  }

  @Test
  public void mapAuthorities_Should_returnEmptyCollection_When_authorityIsEmpty() {
    Collection<? extends GrantedAuthority> mappedAuthorities = this.roleAuthorizationAuthorityMapper
        .mapAuthorities(emptyList());

    assertThat(mappedAuthorities, hasSize(0));
  }

  @Test
  public void mapAuthorities_Should_returnEmptyCollection_When_authoritiesAreNotProvided() {
    List<GrantedAuthority> grantedAuthorities = Stream.of("a", "v", "b", "c")
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    Collection<? extends GrantedAuthority> mappedAuthorities = this.roleAuthorizationAuthorityMapper
        .mapAuthorities(grantedAuthorities);

    assertThat(mappedAuthorities, hasSize(0));
  }

}
