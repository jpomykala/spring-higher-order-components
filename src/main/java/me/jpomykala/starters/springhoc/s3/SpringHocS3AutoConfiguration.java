package me.jpomykala.starters.springhoc.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import me.jpomykala.starters.springhoc.SpringHocAwsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(SpringHocUploadAutoConfiguration.class)
@EnableConfigurationProperties(SpringHocAwsProperties.class)
public class SpringHocS3AutoConfiguration {

  private final SpringHocAwsProperties properties;

  @Autowired
  public SpringHocS3AutoConfiguration(SpringHocAwsProperties properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public AmazonS3 amazonS3() {
    return AmazonS3ClientBuilder.standard()
            .withCredentials(properties.getAWSCredentials())
            .withRegion(properties.getRegions())
            .build();
  }


}
