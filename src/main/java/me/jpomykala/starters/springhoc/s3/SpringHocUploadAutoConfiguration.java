package me.jpomykala.starters.springhoc.s3;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(SpringHocS3AutoConfiguration.class)
@EnableConfigurationProperties(SpringHocS3Properties.class)
public class SpringHocUploadAutoConfiguration {

  private final SpringHocS3Properties properties;
  private final AmazonS3 amazonS3;

  @Autowired
  public SpringHocUploadAutoConfiguration(SpringHocS3Properties properties, AmazonS3 amazonS3) {
    this.properties = properties;
    this.amazonS3 = amazonS3;
  }

  @Bean
  public UploadService uploadService() {
    return new UploadService(properties, amazonS3);
  }

}
