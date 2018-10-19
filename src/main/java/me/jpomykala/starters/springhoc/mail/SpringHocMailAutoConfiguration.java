package me.jpomykala.starters.springhoc.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AmazonSimpleEmailService.class)
@EnableConfigurationProperties(SpringHocMailProperties.class)
public class SpringHocMailAutoConfiguration {

  private final SpringHocMailProperties configuration;
  private final AmazonSimpleEmailService amazonSimpleEmailService;

  @Autowired
  public SpringHocMailAutoConfiguration(SpringHocMailProperties configuration, AmazonSimpleEmailService amazonSimpleEmailService) {
    this.configuration = configuration;
    this.amazonSimpleEmailService = amazonSimpleEmailService;
  }

  @Bean
  public MailService mailSendingService() {
    return new MailService(configuration, amazonSimpleEmailService);
  }
}
