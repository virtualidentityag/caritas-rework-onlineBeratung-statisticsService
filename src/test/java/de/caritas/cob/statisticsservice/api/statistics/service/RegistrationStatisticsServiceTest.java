package de.caritas.cob.statisticsservice.api.statistics.service;

import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.AGENCY_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.ASKER_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.CONSULTING_TYPE_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.TENANT_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.caritas.cob.statisticsservice.api.helper.RegistrationStatisticsDTOConverter;
import de.caritas.cob.statisticsservice.api.model.EventType;
import de.caritas.cob.statisticsservice.api.model.UserRole;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.Agency;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.ConsultingType;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.StatisticEventsContainer;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.StatisticsEvent;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.User;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.meta.ArchiveMetaData;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.meta.DeleteAccountMetaData;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.meta.RegistrationMetaData;
import de.caritas.cob.statisticsservice.api.statistics.repository.StatisticsEventRepository;
import de.caritas.cob.statisticsservice.api.statistics.repository.StatisticsEventTenantAwareRepository;
import de.caritas.cob.statisticsservice.api.tenant.TenantContext;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class RegistrationStatisticsServiceTest {
  @InjectMocks
  RegistrationStatisticsService registrationStatisticsService;
  @Mock
  StatisticsEventRepository statisticsEventRepository;
  @Mock
  StatisticsEventTenantAwareRepository statisticsEventTenantAwareRepository;
  @Spy
  RegistrationStatisticsDTOConverter registrationStatisticsDTOConverter;

  @BeforeEach
  void setup() {
    ReflectionTestUtils.setField(registrationStatisticsService, "multitenancyEnabled", false);
  }

  @Test
  void fetchRegistrationStatisticsData_Should_RetrieveAllStatisticsData_When_MultitenancyEnabledIsDisabled() {
    // when
    registrationStatisticsService.fetchRegistrationStatisticsData();

    // then
    verify(statisticsEventRepository)
        .getAllRegistrationStatistics();
    verify(statisticsEventRepository)
        .getAllRegistrationStatistics();
    verifyNoInteractions(statisticsEventTenantAwareRepository);
  }

  @Test
  void fetchRegistrationStatisticsData_Should_RetrieveTenantAwareStatisticsData_When_MultitenancyEnabledIsEnabled() {
    // given
    ReflectionTestUtils.setField(registrationStatisticsService, "multitenancyEnabled", true);
    TenantContext.setCurrentTenant(TENANT_ID);

    // when
    registrationStatisticsService.fetchRegistrationStatisticsData();

    // then
    verify(statisticsEventTenantAwareRepository)
        .getAllRegistrationStatistics(TENANT_ID);
    verify(statisticsEventTenantAwareRepository)
        .getAllRegistrationStatistics(TENANT_ID);
    verifyNoInteractions(statisticsEventRepository);

    TenantContext.clear();
  }

  @Test
  void fetchRegistrationStatisticsData_Should_RetrieveExpectedData_When_matchingStatisticsAreAvailable() {
    // given
    givenRegistrationStatistics();

    // when
    var result = registrationStatisticsService.fetchRegistrationStatisticsData();

    // then
    verify(registrationStatisticsDTOConverter).convertStatisticsEvent(any(StatisticsEvent.class), any(
        StatisticEventsContainer.class));

    assertThat(result.getRegistrationStatistics().get(0).getUserId(), is(ASKER_ID));
    assertThat(result.getRegistrationStatistics().get(0).getRegistrationDate(),
        is("2022-09-15T09:14:45Z"));
    assertThat(result.getRegistrationStatistics().get(0).getAge(), is(26));
    assertThat(result.getRegistrationStatistics().get(0).getGender(), is("FEMALE"));
    assertThat(result.getRegistrationStatistics().get(0).getMainTopicInternalAttribute(),
        is("alk"));
    assertThat(result.getRegistrationStatistics().get(0).getTopicsInternalAttributes(),
        is(List.of("alk", "drogen")));
    assertThat(result.getRegistrationStatistics().get(0).getPostalCode(), is("12345"));
    assertThat(result.getRegistrationStatistics().get(0).getCounsellingRelation(),
        is("SELF_COUNSELLING"));
  }

  @Test
  void fetchRegistrationStatisticsData_Should_addEndDate() {
    // given
    givenRegistrationStatistics();
    givenArchiveSessionEvents();

    // when
    var result = registrationStatisticsService.fetchRegistrationStatisticsData();

    // then
    verify(registrationStatisticsDTOConverter).convertStatisticsEvent(any(StatisticsEvent.class), any(StatisticEventsContainer.class));

    assertThat(result.getRegistrationStatistics().get(0).getEndDate(), is("end date 1"));

  }

  private void givenRegistrationStatistics() {
    List<StatisticsEvent> testData = new ArrayList<>();
    Object metaData = RegistrationMetaData.builder()
        .registrationDate("2022-09-15T09:14:45Z")
        .age(26)
        .gender("FEMALE")
        .mainTopicInternalAttribute("alk")
        .topicsInternalAttributes(List.of("alk", "drogen"))
        .postalCode("12345")
        .tenantId(1L)
        .counsellingRelation("SELF_COUNSELLING")
        .build();
    testData.add(StatisticsEvent.builder()
        .sessionId(1L)
        .eventType(EventType.REGISTRATION)
        .user(User.builder().userRole(UserRole.ASKER).id(ASKER_ID).build())
        .consultingType(ConsultingType.builder().id(CONSULTING_TYPE_ID).build())
        .agency(Agency.builder().id(AGENCY_ID).build())
        .timestamp(Instant.now())
        .metaData(metaData)
        .build()
    );

    when(statisticsEventRepository.getAllRegistrationStatistics()).thenReturn(testData);
  }

  private void givenArchiveSessionEvents() {
    List<StatisticsEvent> archiveEvents = List.of(archiveSessionEvent(1L, "end date 1"),
        archiveSessionEvent(99L, "end date 2"));
    when(statisticsEventRepository.getAllArchiveSessionEvents()).thenReturn(archiveEvents);
  }

  private void givenDeleteSessionEvents() {
    List<StatisticsEvent> deleteAccountEvents = List.of(archiveSessionEvent(1L, "end date 1"),
        archiveSessionEvent(99L, "end date 2"));
    when(statisticsEventRepository.getAllDeleteAccountSessionEvents()).thenReturn(deleteAccountEvents);
  }

  private StatisticsEvent archiveSessionEvent(Long sessionId, String endDate) {
    Object metaData = ArchiveMetaData.builder().endDate(endDate).build();
    return StatisticsEvent.builder().sessionId(sessionId).metaData(metaData).build();
  }

  private StatisticsEvent deleteAccountEvents(String userId, String deleteDate) {
    User user = new User();
    user.setId(userId);
    Object metaData = DeleteAccountMetaData.builder().deleteDate(deleteDate).build();
    return StatisticsEvent.builder().user(user).metaData(metaData).build();
  }
}
