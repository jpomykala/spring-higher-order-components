package com.jpomykala.springhoc;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "spring-hoc.aws")
public class AmazonProperties {

  @NotNull
  private String accessKey;

  @NotNull
  private String secretKey;

  @NotNull
  private String region;

  public AmazonProperties setAccessKey(String accessKey) {
    this.accessKey = accessKey;
    return this;
  }

  public AmazonProperties setSecretKey(String secretKey) {
    this.secretKey = secretKey;
    return this;
  }

  public AmazonProperties setRegion(String region) {
    this.region = region;
    return this;
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
