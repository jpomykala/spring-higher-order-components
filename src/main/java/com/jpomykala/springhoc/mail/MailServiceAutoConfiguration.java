package com.jpomykala.springhoc.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AmazonSimpleEmailService.class)
@EnableConfigurationProperties(MailServiceProperties.class)
public class MailServiceAutoConfiguration {

  private final MailServiceProperties configuration;
  private final AmazonSimpleEmailService amazonSimpleEmailService;

  @Autowired
  public MailServiceAutoConfiguration(MailServiceProperties configuration, AmazonSimpleEmailService amazonSimpleEmailService) {
    this.configuration = configuration;
    this.amazonSimpleEmailService = amazonSimpleEmailService;
  }

  @Bean
  public MailService mailSendingService() {
    return new MailService(configuration, amazonSimpleEmailService);
  }
}
