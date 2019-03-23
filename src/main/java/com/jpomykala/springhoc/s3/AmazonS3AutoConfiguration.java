package com.jpomykala.springhoc.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.jpomykala.springhoc.AmazonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AmazonProperties.class)
@EnableConfigurationProperties(AmazonProperties.class)
public class AmazonS3AutoConfiguration {

  private final AmazonProperties properties;

  @Autowired
  public AmazonS3AutoConfiguration(AmazonProperties properties) {
    this.properties = properties;
  }

  @Bean
  public AmazonS3 amazonS3() {

    if (properties == null) {
      throw new IllegalArgumentException("properties are null");
    }

    if (properties.getAccessKey() == null) {
      throw new IllegalArgumentException("access key is null");
    }

    return AmazonS3ClientBuilder.standard()
            .withCredentials(properties.getAWSCredentials())
            .withRegion(properties.getRegions())
            .build();
  }
}
