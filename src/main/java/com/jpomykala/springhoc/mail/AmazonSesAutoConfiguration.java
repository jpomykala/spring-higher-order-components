package com.jpomykala.springhoc.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.jpomykala.springhoc.AmazonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AmazonProperties.class)
public class AmazonSesAutoConfiguration {

  private final AmazonProperties properties;

  @Autowired
  public AmazonSesAutoConfiguration(AmazonProperties properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public AmazonSimpleEmailService amazonSimpleEmailService() {
    return AmazonSimpleEmailServiceClientBuilder.standard()
            .withCredentials(properties.getAWSCredentials())
            .withRegion(properties.getRegions())
            .build();
  }


}
