package de.caritas.cob.statisticsservice.api.statistics.service;

import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.CONSULTANT_ID;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.DATE_FROM;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.DATE_TO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.caritas.cob.statisticsservice.api.exception.httpresponses.BadRequestException;
import de.caritas.cob.statisticsservice.api.helper.AuthenticatedUser;
import de.caritas.cob.statisticsservice.api.statistics.repository.StatisticsEventRepository;
import de.caritas.cob.statisticsservice.api.statistics.repository.StatisticsEventRepository.Count;
import de.caritas.cob.statisticsservice.api.statistics.repository.StatisticsEventRepository.Duration;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTest {

  @InjectMocks
  StatisticsService statisticsService;
  @Mock
  StatisticsEventRepository statisticsEventRepository;
  @Mock
  AuthenticatedUser authenticatedUser;

  @Test
  public void fetchStatisticsData_Should_ThrowBadRequestException_WhenDateFromIsAfterDateTo() {
    assertThrows(BadRequestException.class, () -> {
      statisticsService.fetchStatisticsData(DATE_TO, DATE_FROM);
    });
  }

  @Test
  public void fetchStatisticsData_Should_RetrieveStatisticsDataViaRepository() {

    Instant dateFromConverted = OffsetDateTime.of(DATE_FROM, LocalTime.MIN, ZoneOffset.UTC)
        .toInstant();
    Instant dateToConverted = OffsetDateTime.of(DATE_TO, LocalTime.MAX, ZoneOffset.UTC).toInstant();

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    statisticsService.fetchStatisticsData(DATE_FROM, DATE_TO);

    verify(statisticsEventRepository, times(1))
        .calculateNumberOfAssignedSessionsForUser(CONSULTANT_ID, dateFromConverted,
            dateToConverted);
    verify(statisticsEventRepository, times(1))
        .calculateNumberOfSentMessagesForUser(CONSULTANT_ID, dateFromConverted, dateToConverted);
    verify(statisticsEventRepository, times(1))
        .calculateNumbersOfSessionsWhereUserWasActive(CONSULTANT_ID, dateFromConverted,
            dateToConverted);
    verify(statisticsEventRepository, times(1))
        .calculateTimeInVideoCallsForUser(CONSULTANT_ID, dateFromConverted, dateToConverted);
    verify(statisticsEventRepository, times(1))
        .calculateNumbersOfDoneAppointments(eq(CONSULTANT_ID), eq(dateFromConverted), eq(dateToConverted), any(Instant.class));
  }

  @Test
  public void fetchStatisticsData_Should_RetrieveExpectedData_When_matchingStatisticsAreAvailable() {
    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(this.statisticsEventRepository.calculateNumbersOfSessionsWhereUserWasActive(anyString(),
        any(), any())).thenReturn(new Count(2000L));
    when(this.statisticsEventRepository.calculateNumberOfAssignedSessionsForUser(anyString(),
        any(), any())).thenReturn(15L);
    when(this.statisticsEventRepository.calculateNumberOfSentMessagesForUser(anyString(),
        any(), any())).thenReturn(200L);
    when(this.statisticsEventRepository.calculateTimeInVideoCallsForUser(anyString(),
        any(), any())).thenReturn(new Duration(6600L));
    when(this.statisticsEventRepository.calculateNumbersOfDoneAppointments(anyString(),
        any(), any(), any())).thenReturn(new Count(5000L));

    var result = statisticsService.fetchStatisticsData(DATE_FROM, DATE_TO);

    assertThat(result.getNumberOfAssignedSessions(), is(15L));
    assertThat(result.getNumberOfSentMessages(), is(200L));
    assertThat(result.getNumberOfSessionsWhereConsultantWasActive(), is(2000L));
    assertThat(result.getVideoCallDuration(), is(6600L));
    assertThat(result.getNumberOfAppointments(), is(5000L));
  }

  @Test
  public void fetchStatisticsData_Should_RetrieveExpectedFallbackData_When_matchingStatisticsArePartlyNotAvailable() {
    var dateWithoutStatistics = LocalDate.of(1900, 1, 1);
    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(this.statisticsEventRepository.calculateNumbersOfSessionsWhereUserWasActive(anyString(),
        any(), any())).thenReturn(null);
    when(this.statisticsEventRepository.calculateNumberOfAssignedSessionsForUser(anyString(),
        any(), any())).thenReturn(0L);
    when(this.statisticsEventRepository.calculateNumberOfSentMessagesForUser(anyString(),
        any(), any())).thenReturn(0L);
    when(this.statisticsEventRepository.calculateTimeInVideoCallsForUser(anyString(),
        any(), any())).thenReturn(null);
    when(this.statisticsEventRepository.calculateNumbersOfDoneAppointments(anyString(),
        any(), any(), any())).thenReturn(new Count(0));

    var result = statisticsService
        .fetchStatisticsData(dateWithoutStatistics, dateWithoutStatistics);

    assertThat(result.getNumberOfAssignedSessions(), is(0L));
    assertThat(result.getNumberOfSentMessages(), is(0L));
    assertThat(result.getNumberOfSessionsWhereConsultantWasActive(), is(0L));
    assertThat(result.getVideoCallDuration(), is(0L));
    assertThat(result.getNumberOfAppointments(), is(0L));
  }
}
