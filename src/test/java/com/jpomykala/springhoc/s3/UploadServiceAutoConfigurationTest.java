package com.jpomykala.springhoc.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.jpomykala.springhoc.AmazonProperties;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class UploadServiceAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
          .withPropertyValues(
                  "spring-hoc.aws.access-key=xxx",
                  "spring-hoc.aws.secret-key=xxx",
                  "spring-hoc.aws.region=eu-west-1",
                  "spring-hoc.s3.bucket-name=my-bucket"
          )
          .withConfiguration(AutoConfigurations.of(ExampleAnnotationConfiguration.class));


  @Test
  public void shouldConfigureServiceAndAmazonServices() {
    this.contextRunner.run(context -> {
      UploadServiceAutoConfiguration responseWrapper = context.getBean(UploadServiceAutoConfiguration.class);
      assertThat(responseWrapper).isNotNull();

      AmazonS3Properties amazonS3Properties = context.getBean(AmazonS3Properties.class);
      assertThat(amazonS3Properties).isNotNull();
      assertThat(amazonS3Properties.getBucketName()).isNotBlank();

      AmazonS3 amazonS3 = context.getBean(AmazonS3.class);
      assertThat(amazonS3).isNotNull();

      AmazonProperties amazonProperties = context.getBean(AmazonProperties.class);
      assertThat(amazonProperties).isNotNull();
      assertThat(amazonProperties.getAccessKey()).isNotEmpty();
      assertThat(amazonProperties.getSecretKey()).isNotEmpty();
      assertThat(amazonProperties.getRegion()).isNotEmpty();
      assertThat(amazonProperties.getAWSCredentials()).isNotNull();

    });
  }

  @EnableFileUploading
  public static class ExampleAnnotationConfiguration {

  }

}
