package me.jpomykala.starters.springhoc.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import me.jpomykala.starters.springhoc.SpringHocAwsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SpringHocAwsProperties.class)
public class AmazonSesAutoConfiguration {

  @Autowired
  private SpringHocAwsProperties properties;

  @Bean
  @ConditionalOnMissingBean
  public AmazonSimpleEmailService amazonSimpleEmailService() {
    return AmazonSimpleEmailServiceClientBuilder.standard()
            .withCredentials(properties.getAWSCredentials())
            .withRegion(properties.getRegions())
            .build();
  }
}
