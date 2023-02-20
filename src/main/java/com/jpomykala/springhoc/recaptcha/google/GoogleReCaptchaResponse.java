package com.jpomykala.springhoc.recaptcha.google;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GoogleReCaptchaResponse
{
  public GoogleReCaptchaResponse(boolean success, String challengeTs, String hostname, List<String> errorCodes) {
    this.success = success;
    this.challengeTs = challengeTs;
    this.hostname = hostname;
    this.errorCodes = errorCodes;
  }

  private boolean success;

  @JsonProperty("challenge_ts")
  private String challengeTs;

  private String hostname;

  @JsonProperty("error-codes")
  private List<String> errorCodes;


  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getChallengeTs() {
    return challengeTs;
  }

  public void setChallengeTs(String challengeTs) {
    this.challengeTs = challengeTs;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public List<String> getErrorCodes() {
    return errorCodes;
  }

  public void setErrorCodes(List<String> errorCodes) {
    this.errorCodes = errorCodes;
  }
}
