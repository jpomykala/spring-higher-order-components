package me.jpomykala.starters.springhoc;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring-hoc.aws")
public class AmazonProperties {

  private String accessKey;
  private String secretKey;
  private String region;

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public String getRegion() {
    return region;
  }

  public Regions getRegions() {
    return Regions.fromName(region);
  }

  public AWSStaticCredentialsProvider getAWSCredentials() {
    final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
    return new AWSStaticCredentialsProvider(credentials);
  }
}
