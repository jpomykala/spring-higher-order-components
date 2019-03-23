package com.jpomykala.springhoc.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring-hoc.mail")
public class MailServiceProperties {
  private String senderEmailAddress;

  public void setSenderEmailAddress(String senderEmailAddress) {
    this.senderEmailAddress = senderEmailAddress;
  }


  public String getSenderEmailAddress() {
    return senderEmailAddress;
  }
}
