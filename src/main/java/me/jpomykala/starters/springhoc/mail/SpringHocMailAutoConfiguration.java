package me.jpomykala.starters.springhoc.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SpringHocMailProperties.class)
public class SpringHocMailAutoConfiguration {

  @Autowired
  private SpringHocMailProperties configuration;

  @Autowired
  private AmazonSimpleEmailService amazonSimpleEmailService;

  @Bean
  public SpringHocMailService mailSendingService() {
    return new SpringHocMailService(configuration, amazonSimpleEmailService);
  }
}
