package com.jpomykala.springhoc.s3;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AmazonS3AutoConfiguration.class)
@EnableConfigurationProperties(AmazonS3Properties.class)
public class UploadServiceAutoConfiguration {

  private final AmazonS3Properties properties;
  private final AmazonS3 amazonS3;
  private final ApplicationEventPublisher eventPublisher;

  @Autowired
  public UploadServiceAutoConfiguration(AmazonS3Properties properties,
                                        AmazonS3 amazonS3,
                                        ApplicationEventPublisher eventPublisher) {
    this.properties = properties;
    this.amazonS3 = amazonS3;
    this.eventPublisher = eventPublisher;
  }

  @Bean
  public UploadService uploadService() {
    return new UploadService(properties, amazonS3, eventPublisher);
  }

  @Bean
  public UploadRequestListener uploadRequestListener(UploadService uploadService) {
    return new UploadRequestListener(uploadService);
  }

}