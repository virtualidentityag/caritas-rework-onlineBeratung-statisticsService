package de.caritas.cob.statisticsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StatisticsServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(StatisticsServiceApplication.class, args);
  }

}
