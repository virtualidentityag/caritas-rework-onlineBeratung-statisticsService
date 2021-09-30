package de.caritas.cob.statisticsservice.api.controller;

import static de.caritas.cob.statisticsservice.api.testhelper.PathConstants.PATH_GET_CONSULTANT_STATISTICS;
import static de.caritas.cob.statisticsservice.api.testhelper.PathConstants.PATH_GET_CONSULTANT_STATISTICS_CSV;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.CONSULTANT_STATISTICS_RESPONSE_DTO;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.DATE_FROM;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.DATE_FROM_FORMATTED;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.DATE_TO;
import static de.caritas.cob.statisticsservice.api.testhelper.TestConstants.DATE_TO_FORMATTED;
import static org.mockito.Mockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.caritas.cob.statisticsservice.StatisticsServiceApplication;
import de.caritas.cob.statisticsservice.api.authorization.RoleAuthorizationAuthorityMapper;
import de.caritas.cob.statisticsservice.api.service.LogService;
import de.caritas.cob.statisticsservice.api.statistics.service.StatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = StatisticsServiceApplication.class)
public class StatisticsControllerIT {

  @Autowired
  private MockMvc mvc;
  @MockBean
  private RoleAuthorizationAuthorityMapper roleAuthorizationAuthorityMapper;
  @MockBean
  MongoTemplate mongoTemplate;
  @MockBean
  StatisticsService statisticsService;
  @Mock
  private Logger logger;

  @Before
  public void setup() {
    setInternalState(LogService.class, "LOGGER", logger);
  }

  @Test
  public void getConsultantStatistics_Should_ReturnStatisticsDataAndOk() throws Exception {

    when(statisticsService.fetchStatisticsData(DATE_FROM, DATE_TO)).thenReturn(CONSULTANT_STATISTICS_RESPONSE_DTO);

    mvc.perform(
        get(PATH_GET_CONSULTANT_STATISTICS + "?dateFrom=" + DATE_FROM_FORMATTED + "&dateTo=" + DATE_TO_FORMATTED)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(consultantStatisticsResponseDtoToJson()));
  }

  private String consultantStatisticsResponseDtoToJson() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    return objectMapper.writeValueAsString(CONSULTANT_STATISTICS_RESPONSE_DTO);
  }

}
