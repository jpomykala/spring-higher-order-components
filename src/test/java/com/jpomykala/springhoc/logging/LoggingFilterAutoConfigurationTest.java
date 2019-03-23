package com.jpomykala.springhoc.logging;

import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class LoggingFilterAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
          .withConfiguration(AutoConfigurations.of(RequestLoggingAutoConfiguration.class));


  @Test
  public void testDefaultCorsConfiguration() {
    this.contextRunner.run(context -> {
      RequestLoggingAutoConfiguration responseWrapper = context.getBean(RequestLoggingAutoConfiguration.class);
      assertThat(responseWrapper).isNotNull();
    });

  }

}
