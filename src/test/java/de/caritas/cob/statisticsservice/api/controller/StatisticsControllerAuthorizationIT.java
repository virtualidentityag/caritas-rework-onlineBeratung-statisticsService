package de.caritas.cob.statisticsservice.api.controller;

import static de.caritas.cob.statisticsservice.api.testhelper.PathConstants.PATH_GET_CONSULTANT_STATISTICS;
import static de.caritas.cob.statisticsservice.api.testhelper.PathConstants.PATH_GET_CONSULTANT_STATISTICS_CSV;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.caritas.cob.statisticsservice.StatisticsServiceApplication;
import de.caritas.cob.statisticsservice.api.authorization.Authority.AuthorityValue;
import de.caritas.cob.statisticsservice.api.statistics.repository.StatisticsEventRepository;
import de.caritas.cob.statisticsservice.api.statistics.repository.StatisticsEventTenantAwareRepository;
import org.junit.jupiter.api.Test;

import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@TestPropertySource(properties = "spring.profiles.active=testing")
@SpringBootTest(classes = {StatisticsServiceApplication.class})
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@AutoConfigureMockMvc
public class StatisticsControllerAuthorizationIT {

  private final String CSRF_COOKIE = "csrfCookie";
  private final String CSRF_HEADER = "csrfHeader";
  private final String CSRF_VALUE = "test";
  private final Cookie csrfCookie = new Cookie(CSRF_COOKIE, CSRF_VALUE);

  @Autowired
  private MockMvc mvc;
  @MockBean
  MongoTemplate mongoTemplate;
  @MockBean
  StatisticsEventRepository statisticsEventRepository;

  @MockBean
  StatisticsEventTenantAwareRepository statisticsEventTenantAwareRepository;

  @Test
  @WithMockUser(authorities = {AuthorityValue.CONSULTANT_DEFAULT})
  public void getConsultantStatistics_Should_ReturnOK_When_ProperlyAuthorizedWithConsultantAuthority()
      throws Exception {
    this.mvc.perform(get(PATH_GET_CONSULTANT_STATISTICS)
            .cookie(csrfCookie)
            .header(CSRF_HEADER, CSRF_VALUE)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser
  public void getConsultantStatistics_Should_ReturnForbidden_When_NoConsultantDefaultAuthority()
      throws Exception {
    this.mvc.perform(get(PATH_GET_CONSULTANT_STATISTICS)
            .cookie(csrfCookie)
            .header(CSRF_HEADER, CSRF_VALUE)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

}
