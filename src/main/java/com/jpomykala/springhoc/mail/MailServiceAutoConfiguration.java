package com.jpomykala.springhoc.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AmazonSimpleEmailService.class)
public class MailServiceAutoConfiguration {

  private final AmazonSimpleEmailService amazonSimpleEmailService;

  @Autowired
  public MailServiceAutoConfiguration(AmazonSimpleEmailService amazonSimpleEmailService) {
    this.amazonSimpleEmailService = amazonSimpleEmailService;
  }

  @Bean
  public MailService mailSendingService() {
    return new MailService(amazonSimpleEmailService);
  }
}
