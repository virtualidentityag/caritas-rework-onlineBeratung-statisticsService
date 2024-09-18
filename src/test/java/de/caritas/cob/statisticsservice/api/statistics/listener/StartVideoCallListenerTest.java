package de.caritas.cob.statisticsservice.api.statistics.listener;

import de.caritas.cob.statisticsservice.api.model.EventType;
import de.caritas.cob.statisticsservice.api.model.StartVideoCallStatisticsEventMessage;
import de.caritas.cob.statisticsservice.api.model.UserRole;
import de.caritas.cob.statisticsservice.api.service.UserStatisticsService;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.StatisticsEvent;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.meta.StartVideoCallMetaData;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.meta.VideoCallStatus;
import de.caritas.cob.statisticsservice.userstatisticsservice.generated.web.model.SessionStatisticsResultDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.AGENCY_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.CONSULTANT_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.CONSULTING_TYPE_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.RC_GROUP_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.SESSION_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.VIDEO_CALL_UUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class StartVideoCallListenerTest {

  @InjectMocks
  StartVideoCallListener startVideoCallListener;
  @Mock
  MongoTemplate mongoTemplate;
  @Mock
  UserStatisticsService userStatisticsService;
  @Captor
  ArgumentCaptor<StatisticsEvent> statisticsEventCaptor;

  @Test
  public void receiveMessage_Should_saveEventWithSessionToMongoDb() {
    // given
    SessionStatisticsResultDTO sessionStatisticsResultDTO = buildResultDto();
    when(userStatisticsService.retrieveSessionViaSessionId(SESSION_ID))
        .thenReturn(sessionStatisticsResultDTO);

    StartVideoCallStatisticsEventMessage startVideoCallStatisticsEventMessage = buildEventMessageWithSession();

    // when
    startVideoCallListener.receiveMessage(startVideoCallStatisticsEventMessage);

    verify(mongoTemplate).insert(statisticsEventCaptor.capture());

    StatisticsEvent statisticsEvent = statisticsEventCaptor.getValue();
    assertThat(
        statisticsEvent.getEventType(), is(startVideoCallStatisticsEventMessage.getEventType()));
    assertThat(statisticsEvent.getSessionId(), is(sessionStatisticsResultDTO.getId()));
    assertThat(
        statisticsEvent.getConsultingType().getId(),
        is(sessionStatisticsResultDTO.getConsultingType()));
    assertThat(statisticsEvent.getAgency().getId(), is(sessionStatisticsResultDTO.getAgencyId()));
    assertThat(
        statisticsEvent.getTimestamp(),
        is(
            startVideoCallStatisticsEventMessage
                .getTimestamp()
                .truncatedTo(ChronoUnit.SECONDS)
                .toInstant()));
    assertThat(
        statisticsEvent.getUser().getId(), is(startVideoCallStatisticsEventMessage.getUserId()));
    assertThat(statisticsEvent.getUser().getUserRole(), is(UserRole.CONSULTANT));
    assertThat(
        statisticsEvent.getMetaData(), is(buildMetaData(startVideoCallStatisticsEventMessage)));
  }

  @Test
  public void receiveMessage_Should_saveEventWithoutSessionToMongoDb() {
    when(userStatisticsService.retrieveSessionViaSessionId(SESSION_ID))
            .thenReturn(buildResultDto());

    var startVideoCallStatisticsEventMessage = buildEventMessageWithoutSession();

    startVideoCallListener.receiveMessage(startVideoCallStatisticsEventMessage);

    verify(mongoTemplate).insert(statisticsEventCaptor.capture());

    var statisticsEvent = statisticsEventCaptor.getValue();
    assertThat(statisticsEvent.getEventType(), is(startVideoCallStatisticsEventMessage.getEventType()));
    assertThat(statisticsEvent.getConsultingType(), is(nullValue()));
    assertThat(statisticsEvent.getAgency(), is(nullValue()));
    assertThat(
            statisticsEvent.getTimestamp(),
            is(startVideoCallStatisticsEventMessage.getTimestamp().truncatedTo(ChronoUnit.SECONDS).toInstant())
    );
    assertThat(statisticsEvent.getUser().getId(), is(startVideoCallStatisticsEventMessage.getUserId()));
    assertThat(statisticsEvent.getUser().getUserRole(), is(UserRole.CONSULTANT));
    assertThat(statisticsEvent.getMetaData(), is(buildMetaData(startVideoCallStatisticsEventMessage)));
  }

  private SessionStatisticsResultDTO buildResultDto() {
    return new SessionStatisticsResultDTO()
        .id(SESSION_ID)
        .isTeamSession(false)
        .agencyId(AGENCY_ID)
        .consultingType(CONSULTING_TYPE_ID)
        .rcGroupId(RC_GROUP_ID);
  }

  private StartVideoCallStatisticsEventMessage buildEventMessageWithSession() {
    return new StartVideoCallStatisticsEventMessage()
        .sessionId(SESSION_ID)
        .eventType(EventType.CREATE_MESSAGE)
        .userId(CONSULTANT_ID)
        .userRole(UserRole.CONSULTANT)
        .timestamp(OffsetDateTime.now())
        .videoCallUuid(VIDEO_CALL_UUID);
  }

  private StartVideoCallStatisticsEventMessage buildEventMessageWithoutSession() {
    return new StartVideoCallStatisticsEventMessage()
            .eventType(EventType.START_VIDEO_CALL)
            .userId(CONSULTANT_ID)
            .userRole(UserRole.CONSULTANT)
            .timestamp(OffsetDateTime.now())
            .videoCallUuid(VIDEO_CALL_UUID);
  }

  private StartVideoCallMetaData buildMetaData(StartVideoCallStatisticsEventMessage eventMessage) {
    return StartVideoCallMetaData.builder()
        .videoCallUuid(eventMessage.getVideoCallUuid())
        .duration(0)
        .timestampStop(null)
        .status(VideoCallStatus.ONGOING)
        .build();
  }
}
