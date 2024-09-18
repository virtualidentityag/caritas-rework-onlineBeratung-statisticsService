package de.caritas.cob.statisticsservice.api.statistics.listener;

import de.caritas.cob.statisticsservice.api.model.AssignSessionStatisticsEventMessage;
import de.caritas.cob.statisticsservice.api.model.EventType;
import de.caritas.cob.statisticsservice.api.model.UserRole;
import de.caritas.cob.statisticsservice.api.service.UserStatisticsService;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.StatisticsEvent;
import de.caritas.cob.statisticsservice.userstatisticsservice.generated.web.model.SessionStatisticsResultDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.AGENCY_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.CONSULTANT_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.CONSULTING_TYPE_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.RC_GROUP_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.SESSION_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AssignSessionListenerTest {

  @InjectMocks
  AssignSessionListener assignSessionListener;
  @Mock MongoTemplate mongoTemplate;
  @Mock UserStatisticsService userStatisticsService;
  @Captor ArgumentCaptor<StatisticsEvent> statisticsEventCaptor;

  @Test
  void receiveMessage_Should_saveFullEventToMongoDb() {
    // given
    SessionStatisticsResultDTO sessionStatisticsResultDTO = buildResultDto();
    when(userStatisticsService.retrieveSessionViaSessionId(SESSION_ID))
        .thenReturn(sessionStatisticsResultDTO);

    AssignSessionStatisticsEventMessage assignSessionStatisticsEventMessage = buildEventMessage(true);

    // when
    assignSessionListener.receiveMessage(assignSessionStatisticsEventMessage);

    // then
    verify(mongoTemplate).insert(statisticsEventCaptor.capture());

    StatisticsEvent statisticsEvent = statisticsEventCaptor.getValue();
    assertThat(statisticsEvent.getEventType(), is(assignSessionStatisticsEventMessage.getEventType()));
    assertThat(statisticsEvent.getSessionId(), is(sessionStatisticsResultDTO.getId()));
    assertThat(statisticsEvent.getConsultingType().getId(), is(sessionStatisticsResultDTO.getConsultingType()));
    assertThat(statisticsEvent.getAgency().getId(), is(sessionStatisticsResultDTO.getAgencyId()));
    assertThat(statisticsEvent.getTimestamp(), is(assignSessionStatisticsEventMessage.getTimestamp().toInstant()));
    assertThat(statisticsEvent.getUser().getId(), is(assignSessionStatisticsEventMessage.getUserId()));
    assertThat(statisticsEvent.getUser().getUserRole(), is(UserRole.CONSULTANT));

    var metaData = statisticsEvent.getMetaData();
    assertThat(metaData, isA(Map.class));

    @SuppressWarnings("unchecked")
    var metaMap = (Map<String, String>) metaData;
    assertThat(metaMap.get("requestReferer"), is(assignSessionStatisticsEventMessage.getRequestReferer()));
    assertThat(metaMap.get("requestUri"), is(assignSessionStatisticsEventMessage.getRequestUri()));
    assertThat(metaMap.get("requestUserId"), is(assignSessionStatisticsEventMessage.getRequestUserId()));
    assertThat(metaMap.containsKey(RandomStringUtils.randomAlphanumeric(64)), is(false));
  }

  @Test
  void receiveMessage_Should_savePartialEventToMongoDb() {
    var sessionStatisticsResultDTO = buildResultDto();
    when(userStatisticsService.retrieveSessionViaSessionId(SESSION_ID))
            .thenReturn(sessionStatisticsResultDTO);

    var assignSessionStatisticsEventMessage = buildEventMessage(false);

    // when
    assignSessionListener.receiveMessage(assignSessionStatisticsEventMessage);

    // then
    verify(mongoTemplate).insert(statisticsEventCaptor.capture());

    StatisticsEvent statisticsEvent = statisticsEventCaptor.getValue();
    assertThat(statisticsEvent.getEventType(), is(assignSessionStatisticsEventMessage.getEventType()));
    assertThat(statisticsEvent.getSessionId(), is(sessionStatisticsResultDTO.getId()));
    assertThat(statisticsEvent.getConsultingType().getId(), is(sessionStatisticsResultDTO.getConsultingType()));
    assertThat(statisticsEvent.getAgency().getId(), is(sessionStatisticsResultDTO.getAgencyId()));
    assertThat(statisticsEvent.getTimestamp(), is(assignSessionStatisticsEventMessage.getTimestamp().toInstant()));
    assertThat(statisticsEvent.getUser().getId(), is(assignSessionStatisticsEventMessage.getUserId()));
    assertThat(statisticsEvent.getUser().getUserRole(), is(UserRole.CONSULTANT));

    var metaData = statisticsEvent.getMetaData();
    assertThat(metaData, isA(Map.class));

    @SuppressWarnings("unchecked")
    var metaMap = (Map<String, String>) metaData;
    assertThat(metaMap.containsKey(RandomStringUtils.randomAlphanumeric(64)), is(false));
    assertThat(metaMap.get("requestUri"), is(assignSessionStatisticsEventMessage.getRequestUri()));
    assertNull(metaMap.get("requestReferer"));
    assertNull(metaMap.get("requestUri"));
  }

  private SessionStatisticsResultDTO buildResultDto() {
    return new SessionStatisticsResultDTO()
        .id(SESSION_ID)
        .isTeamSession(false)
        .agencyId(AGENCY_ID)
        .consultingType(CONSULTING_TYPE_ID)
        .rcGroupId(RC_GROUP_ID);
  }

  private AssignSessionStatisticsEventMessage buildEventMessage(boolean full) {
    var message = new AssignSessionStatisticsEventMessage()
            .sessionId(SESSION_ID)
            .eventType(EventType.ASSIGN_SESSION)
            .userId(CONSULTANT_ID)
            .userRole(UserRole.CONSULTANT)
            .timestamp(OffsetDateTime.now())
            .requestUserId(UUID.randomUUID().toString());

    if (full) {
      message.requestReferer(RandomStringUtils.randomAlphanumeric(64))
              .requestUri(RandomStringUtils.randomAlphanumeric(64));
    }

    return message;
  }
}
