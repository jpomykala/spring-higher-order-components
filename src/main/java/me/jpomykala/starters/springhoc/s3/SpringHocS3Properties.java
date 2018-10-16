package me.jpomykala.starters.springhoc.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring-hoc.s3")
public class SpringHocS3Properties {

  private String bucketName;

  public String getBucketName() {
    return bucketName;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }
}
