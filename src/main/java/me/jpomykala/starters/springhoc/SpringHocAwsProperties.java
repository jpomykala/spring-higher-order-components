package me.jpomykala.starters.springhoc;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring-hoc.aws")
public class SpringHocAwsProperties {

  private String accessToken;
  private String secretKey;
  private String region;

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public String getRegion() {
    return region;
  }

  public Regions getRegions() {
    return Regions.fromName(getRegion());
  }

  public AWSStaticCredentialsProvider getAWSCredentials() {
    final String accessKey = getAccessToken();
    final String secretKey = getSecretKey();
    final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
    return new AWSStaticCredentialsProvider(credentials);
  }
}
