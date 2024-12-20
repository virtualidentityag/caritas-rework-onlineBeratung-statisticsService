package de.caritas.cob.statisticsservice.api.statistics.listener;

import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.AGENCY_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.ASKER_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.CONSULTING_TYPE_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.RC_GROUP_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.SESSION_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.TENANT_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.caritas.cob.statisticsservice.api.model.EventType;
import de.caritas.cob.statisticsservice.api.model.RegistrationStatisticsEventMessage;
import de.caritas.cob.statisticsservice.api.model.UserRole;
import de.caritas.cob.statisticsservice.api.service.UserStatisticsService;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.StatisticsEvent;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.meta.RegistrationMetaData;
import de.caritas.cob.statisticsservice.userstatisticsservice.generated.web.model.SessionStatisticsResultDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.OffsetDateTime;
import java.util.List;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class RegistrationListenerTest {

  @InjectMocks
  RegistrationListener registrationListener;
  @Mock
  MongoTemplate mongoTemplate;
  @Mock
  UserStatisticsService userStatisticsService;
  @Captor
  ArgumentCaptor<StatisticsEvent> statisticsEventCaptor;

  @Test
  public void registration_Should_saveEventToMongoDb() {
    // given
    SessionStatisticsResultDTO sessionStatisticsResultDTO = buildResultDto();
    when(userStatisticsService.retrieveSessionViaSessionId(SESSION_ID))
        .thenReturn(sessionStatisticsResultDTO);

    RegistrationStatisticsEventMessage registrationStatisticsEventMessage = buildEventMessage();

    // when
    registrationListener.receiveMessage(registrationStatisticsEventMessage);

    // then
    verify(mongoTemplate).insert(statisticsEventCaptor.capture());

    StatisticsEvent statisticsEvent = statisticsEventCaptor.getValue();
    assertThat(statisticsEvent.getEventType(),
        is(registrationStatisticsEventMessage.getEventType()));
    assertThat(statisticsEvent.getSessionId(), is(sessionStatisticsResultDTO.getId()));
    assertThat(statisticsEvent.getConsultingType().getId(),
        is(sessionStatisticsResultDTO.getConsultingType()));
    assertThat(statisticsEvent.getAgency().getId(), is(sessionStatisticsResultDTO.getAgencyId()));
    assertThat(statisticsEvent.getTimestamp(),
        is(registrationStatisticsEventMessage.getTimestamp().toInstant()));
    assertThat(statisticsEvent.getUser().getId(),
        is(registrationStatisticsEventMessage.getUserId()));
    assertThat(statisticsEvent.getUser().getUserRole(), is(UserRole.ASKER));
    assertThat(statisticsEvent.getMetaData(),
        is(buildMetaData(registrationStatisticsEventMessage)));
  }

  private SessionStatisticsResultDTO buildResultDto() {
    return new SessionStatisticsResultDTO()
        .id(SESSION_ID)
        .isTeamSession(false)
        .agencyId(AGENCY_ID)
        .consultingType(CONSULTING_TYPE_ID)
        .rcGroupId(RC_GROUP_ID);
  }

  private RegistrationStatisticsEventMessage buildEventMessage() {
    return new RegistrationStatisticsEventMessage()
        .tenantId(TENANT_ID)
        .sessionId(SESSION_ID)
        .eventType(EventType.CREATE_MESSAGE)
        .userId(ASKER_ID)
        .userRole(UserRole.ASKER)
        .registrationDate("2022-08-15T21:11:29")
        .age(25)
        .gender("FEMALE")
        .counsellingRelation("SELF_COUNSELLING")
        .topicsInternalAttributes(List.of("angeho01", "angeho13"))
        .mainTopicInternalAttribute("angeho01")
        .postalCode("99999")
        .referer("aReferer")
        .timestamp(OffsetDateTime.now());

  }

  private RegistrationMetaData buildMetaData(RegistrationStatisticsEventMessage eventMessage) {
    return RegistrationMetaData.builder()
        .tenantId(TENANT_ID)
        .registrationDate(eventMessage.getRegistrationDate())
        .age(eventMessage.getAge())
        .gender(eventMessage.getGender())
        .counsellingRelation(eventMessage.getCounsellingRelation())
        .topicsInternalAttributes(eventMessage.getTopicsInternalAttributes())
        .mainTopicInternalAttribute(eventMessage.getMainTopicInternalAttribute())
        .postalCode(eventMessage.getPostalCode())
        .referer(eventMessage.getReferer())
        .build();
  }

}
