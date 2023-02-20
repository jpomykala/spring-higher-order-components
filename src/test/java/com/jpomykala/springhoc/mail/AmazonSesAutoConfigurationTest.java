package com.jpomykala.springhoc.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.jpomykala.springhoc.AmazonProperties;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AmazonSesAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
          .withPropertyValues(
                  "spring-hoc.aws.access-key=xxx",
                  "spring-hoc.aws.secret-key=xxx",
                  "spring-hoc.aws.region=eu-west-1"
          )
          .withConfiguration(AutoConfigurations.of(AmazonSesAutoConfigurationTest.ExampleAnnotationConfiguration.class));


  @Test
  public void shouldConfigureServiceAndAmazonServices() {
    this.contextRunner.run(context -> {
      AmazonSimpleEmailService responseWrapper = context.getBean(AmazonSimpleEmailService.class);
      assertThat(responseWrapper).isNotNull();

      AmazonProperties amazonProperties = context.getBean(AmazonProperties.class);
      assertThat(amazonProperties).isNotNull();
      assertThat(amazonProperties.getAccessKey()).isNotEmpty();
      assertThat(amazonProperties.getSecretKey()).isNotEmpty();
      assertThat(amazonProperties.getRegion()).isNotEmpty();
      assertThat(amazonProperties.getAWSCredentials()).isNotNull();

    });
  }

  @EnableEmailSending
  public static class ExampleAnnotationConfiguration {

  }


}
