package de.caritas.cob.statisticsservice.api.statistics.listener;

import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.AGENCY_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.CONSULTANT_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.CONSULTING_TYPE_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.RC_GROUP_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.SESSION_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.TENANT_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.caritas.cob.statisticsservice.api.model.ArchiveOrDeleteSessionStatisticsEventMessage;
import de.caritas.cob.statisticsservice.api.model.EventType;
import de.caritas.cob.statisticsservice.api.model.UserRole;
import de.caritas.cob.statisticsservice.api.service.UserStatisticsService;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.StatisticsEvent;
import de.caritas.cob.statisticsservice.api.statistics.model.statisticsevent.meta.ArchiveMetaData;
import de.caritas.cob.statisticsservice.userstatisticsservice.generated.web.model.SessionStatisticsResultDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.OffsetDateTime;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ArchiveOrDeleteSessionListenerTest {

  @InjectMocks
  ArchiveOrDeleteSessionListener archiveSessionListener;
  @Mock
  MongoTemplate mongoTemplate;
  @Mock
  UserStatisticsService userStatisticsService;
  @Captor
  ArgumentCaptor<StatisticsEvent> statisticsEventCaptor;

  @Test
  public void receiveMessage_Should_saveEventToMongoDb() {
    // given
    SessionStatisticsResultDTO sessionStatisticsResultDTO = buildResultDto();
    when(userStatisticsService.retrieveSessionViaSessionId(SESSION_ID))
        .thenReturn(sessionStatisticsResultDTO);

    OffsetDateTime timestamp = OffsetDateTime.now();
    ArchiveOrDeleteSessionStatisticsEventMessage archiveSessionStatisticsEventMessage = buildEventMessage(timestamp);

    // when
    archiveSessionListener.receiveMessage(archiveSessionStatisticsEventMessage);

    // then
    verify(mongoTemplate).insert(statisticsEventCaptor.capture());

    StatisticsEvent statisticsEvent = statisticsEventCaptor.getValue();
    assertThat(statisticsEvent.getEventType(), is(EventType.ARCHIVE_SESSION));
    assertThat(statisticsEvent.getSessionId(), is(SESSION_ID));
    assertThat(statisticsEvent.getConsultingType().getId(), is(CONSULTING_TYPE_ID));
    assertThat(statisticsEvent.getAgency().getId(), is(AGENCY_ID));
    assertThat(statisticsEvent.getTimestamp(), is(timestamp.toInstant()));
    assertThat(statisticsEvent.getUser().getId(), is(CONSULTANT_ID));
    assertThat(statisticsEvent.getUser().getUserRole(), is(UserRole.CONSULTANT));
    assertThat(statisticsEvent.getMetaData(), is(buildMetaData()));
  }

  private SessionStatisticsResultDTO buildResultDto() {
    return new SessionStatisticsResultDTO()
        .id(SESSION_ID)
        .isTeamSession(false)
        .agencyId(AGENCY_ID)
        .consultingType(CONSULTING_TYPE_ID)
        .rcGroupId(RC_GROUP_ID);
  }

  private ArchiveOrDeleteSessionStatisticsEventMessage buildEventMessage(OffsetDateTime timestamp) {
    return new ArchiveOrDeleteSessionStatisticsEventMessage()
        .sessionId(SESSION_ID)
        .tenantId(TENANT_ID)
        .eventType(EventType.ARCHIVE_SESSION)
        .userId(CONSULTANT_ID)
        .userRole(UserRole.CONSULTANT)
        .timestamp(timestamp)
        .endDate("2022-10-14T10:43:29");
  }

  private ArchiveMetaData buildMetaData() {
    return ArchiveMetaData.builder()
        .endDate("2022-10-14T10:43:29")
        .tenantId(TENANT_ID)
        .build();
  }
}
