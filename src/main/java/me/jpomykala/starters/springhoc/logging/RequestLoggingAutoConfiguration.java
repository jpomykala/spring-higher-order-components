package me.jpomykala.starters.springhoc.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestLoggingAutoConfiguration {

  @Bean
  public LoggingFilterFactory loggingFilterFactory() {
    return new LoggingFilterFactory();
  }
}
