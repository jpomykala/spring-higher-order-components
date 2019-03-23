package com.jpomykala.springhoc.cors;

import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SpringHocCORSAutoConfigurationTest {


  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
          .withConfiguration(AutoConfigurations.of(CorsAutoConfiguration.class));


  @Test
  public void testDefaultCorsConfiguration() {
    this.contextRunner.run(context -> {
      FilterRegistrationBean filterRegistrationBean = context.getBean("corsFilter", FilterRegistrationBean.class);
      assertThat(filterRegistrationBean).isNotNull();
      assertThat(filterRegistrationBean.getOrder()).isEqualTo(Ordered.HIGHEST_PRECEDENCE);
    });

  }
}
