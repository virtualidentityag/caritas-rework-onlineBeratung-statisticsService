package de.caritas.cob.statisticsservice.api.statistics.listener;

import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.AGENCY_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.CONSULTANT_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.CONSULTING_TYPE_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.MONGO_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.SESSION_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.VIDEO_CALL_UUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

import de.caritas.cob.statisticsservice.api.model.EventType;
import de.caritas.cob.statisticsservice.api.model.StopVideoCallStatisticsEventMessage;
import de.caritas.cob.statisticsservice.api.model.UserRole;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.Agency;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.ConsultingType;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.StatisticsEvent;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.User;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.meta.StartVideoCallMetaData;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.meta.VideoCallStatus;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.AmqpException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class StopVideoCallListenerTest {

  @InjectMocks
  StopVideoCallListener stopVideoCallListener;
  @Mock
  MongoTemplate mongoTemplate;

  @Test
  public void receiveMessage_Should_saveEventToMongoDb() {

    when(mongoTemplate.find(Mockito.any(Query.class), eq(StatisticsEvent.class)))
        .thenReturn(buildMongoResultWithOneStatisticsEvent());

    StopVideoCallStatisticsEventMessage stopVideoCallStatisticsEventMessage = buildEventMessage();
    stopVideoCallListener.receiveMessage(stopVideoCallStatisticsEventMessage);

    verify(mongoTemplate, times(1)).save(any(StatisticsEvent.class));

    ArgumentCaptor<StatisticsEvent> statisticsEventCaptor =
        ArgumentCaptor.forClass(StatisticsEvent.class);
    verify(mongoTemplate).save(statisticsEventCaptor.capture());
    StatisticsEvent statisticsEvent = statisticsEventCaptor.getValue();
    assertThat(
        statisticsEvent.getEventType(), is(EventType.START_VIDEO_CALL));
    assertThat(statisticsEvent.getSessionId(), is(SESSION_ID));
    assertThat(statisticsEvent.getConsultingType().getId(), is(CONSULTING_TYPE_ID));
    assertThat(statisticsEvent.getAgency().getId(), is(AGENCY_ID));
    assertThat(
        statisticsEvent.getTimestamp(),
        is(
            stopVideoCallStatisticsEventMessage
                .getTimestamp()
                .truncatedTo(ChronoUnit.SECONDS)
                .toInstant()));
    assertThat(
        statisticsEvent.getUser().getId(), is(CONSULTANT_ID));
    assertThat(statisticsEvent.getUser().getUserRole(), is(UserRole.CONSULTANT));
    StartVideoCallMetaData startVideoCallMetaData = (StartVideoCallMetaData) statisticsEvent.getMetaData();
    assertThat(startVideoCallMetaData.getVideoCallUuid(), is(VIDEO_CALL_UUID));
    assertThat(startVideoCallMetaData.getStatus(), is(VideoCallStatus.FINISHED));
    assertThat(startVideoCallMetaData.getTimestampStop(), is(stopVideoCallStatisticsEventMessage.getTimestamp().toInstant()));
    long duration = Duration
        .between(
            statisticsEvent.getTimestamp(),
            stopVideoCallStatisticsEventMessage.getTimestamp().toInstant())
        .getSeconds();
    assertThat(startVideoCallMetaData.getDuration(), is(duration));
  }

  @Test(expected = AmqpException.class)
  public void receiveMessage_Should_ThrowAmqpException_WhenMoreThanOneStartVideoCallEventWasFound() {

    when(mongoTemplate.find(Mockito.any(Query.class), eq(StatisticsEvent.class)))
        .thenReturn(buildMongoResultWithTwoStatisticsEvent());

    StopVideoCallStatisticsEventMessage stopVideoCallStatisticsEventMessage = buildEventMessage();
    stopVideoCallListener.receiveMessage(stopVideoCallStatisticsEventMessage);

  }

  @Test(expected = AmqpException.class)
  public void receiveMessage_Should_ThrowAmqpException_WhenNoStartVideoCallEventWasFound() {

    when(mongoTemplate.find(Mockito.any(Query.class), eq(StatisticsEvent.class)))
        .thenReturn(Collections.emptyList());

    StopVideoCallStatisticsEventMessage stopVideoCallStatisticsEventMessage = buildEventMessage();
    stopVideoCallListener.receiveMessage(stopVideoCallStatisticsEventMessage);

  }

  public List<StatisticsEvent> buildMongoResultWithOneStatisticsEvent() {
    return new ArrayList<>(
        List.of(
            StatisticsEvent.builder()
                .sessionId(SESSION_ID)
                .consultingType(ConsultingType.builder().id(CONSULTING_TYPE_ID).build())
                .agency(Agency.builder().id(AGENCY_ID).build())
                .eventType(EventType.START_VIDEO_CALL)
                .timestamp(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).toInstant())
                .user(User.builder().userRole(UserRole.CONSULTANT).id(CONSULTANT_ID).build())
                .id(MONGO_ID)
                .metaData(
                    StartVideoCallMetaData.builder()
                        .videoCallUuid(VIDEO_CALL_UUID)
                        .status(VideoCallStatus.ONGOING)
                        .duration(0)
                        .timestampStop(null)
                        .build())
                .build()));
  }

  public List<StatisticsEvent> buildMongoResultWithTwoStatisticsEvent() {
    return new ArrayList<>(
        List.of(StatisticsEvent.builder().build(), StatisticsEvent.builder().build()));
  }

  private StopVideoCallStatisticsEventMessage buildEventMessage() {
    return new StopVideoCallStatisticsEventMessage()
        .videoCallUuid(VIDEO_CALL_UUID)
        .eventType(EventType.STOP_VIDEO_CALL)
        .userRole(null)
        .userId(null)
        .timestamp(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
  }
}
