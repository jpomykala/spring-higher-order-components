package me.jpomykala.starters.springhoc.s3;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SpringHocS3Properties.class)
public class SpringHocUploadAutoConfiguration {

  @Autowired
  private SpringHocS3Properties properties;

  @Autowired
  private AmazonS3 amazonS3;

  @Bean
  @ConditionalOnMissingBean(search = SearchStrategy.ALL)
  public UploadService uploadService() {
    return new UploadService(properties, amazonS3);
  }

}
