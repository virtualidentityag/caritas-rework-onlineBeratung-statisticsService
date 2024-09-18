package de.caritas.cob.statisticsservice.api.service;


import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.RC_GROUP_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.SESSION_ID;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.caritas.cob.statisticsservice.api.service.securityheader.SecurityHeaderSupplier;
import de.caritas.cob.statisticsservice.api.service.securityheader.TenantHeaderSupplier;
import de.caritas.cob.statisticsservice.config.apiclient.UserStatisticsApiControllerFactory;
import de.caritas.cob.statisticsservice.userstatisticsservice.generated.web.UserStatisticsControllerApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
class UserStatisticsServiceTest {

  @InjectMocks
  UserStatisticsService userStatisticsService;
  @Mock
  UserStatisticsControllerApi userStatisticsControllerApi;
  @Mock
  SecurityHeaderSupplier securityHeaderSupplier;
  @Mock
  TenantHeaderSupplier tenantHeaderSupplier;

  @Mock
  UserStatisticsApiControllerFactory userStatisticsApiControllerFactory;

  @BeforeEach
  void setup() {
    when(userStatisticsApiControllerFactory.createControllerApi()).thenReturn(userStatisticsControllerApi);
  }

  @Test
  void retrieveSessionViaRcGroupId_Should_RetrieveSessionViaUserStatisticsControllerApi() {

    var headers = new HttpHeaders();
    when(securityHeaderSupplier.getCsrfHttpHeaders()).thenReturn(headers);
    userStatisticsService.retrieveSessionViaRcGroupId(RC_GROUP_ID);
    verify(userStatisticsControllerApi, times(1)).getSession(null, RC_GROUP_ID);
    verify(tenantHeaderSupplier).addTechnicalTenantHeaderIfMultitenancyEnabled(headers);
  }

  @Test
  void retrieveSessionViaSessionId_Should_RetrieveSessionViaUserStatisticsControllerApi() {

    var headers = new HttpHeaders();
    when(securityHeaderSupplier.getCsrfHttpHeaders()).thenReturn(headers);
    userStatisticsService.retrieveSessionViaSessionId(SESSION_ID);
    verify(userStatisticsControllerApi, times(1)).getSession(SESSION_ID, null);
    verify(tenantHeaderSupplier, times(1)).addTechnicalTenantHeaderIfMultitenancyEnabled(headers);
  }

}
