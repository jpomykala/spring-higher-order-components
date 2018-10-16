package me.jpomykala.starters.springhoc.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hoc.mail")
public class SpringHocMailProperties {
  private String senderEmailAddress;

  public void setSenderEmailAddress(String senderEmailAddress) {
    this.senderEmailAddress = senderEmailAddress;
  }


  public String getSenderEmailAddress() {
    return senderEmailAddress;
  }
}
