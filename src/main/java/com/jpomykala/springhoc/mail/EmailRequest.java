package com.jpomykala.springhoc.mail;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;

import java.util.*;

public class EmailRequest {

  private final Destination destination;
  private final Set<String> replyTo;
  private final Message message;
  private final String source;


  public Destination getDestination() {
    return destination;
  }

  public Set<String> getReplyTo() {
    return replyTo;
  }

  public Message getMessage() {
    return message;
  }

  public String getSource() {
    return source;
  }

  protected EmailRequest(Builder builder) {
    this.destination = builder.destination;
    this.message = builder.message;
    this.source = builder.source;
    this.replyTo = new HashSet<>(builder.replyTo);
  }


  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final Message message;
    private Destination destination;
    private String source;
    private final Collection<String> replyTo;

    public Builder() {
      message = new Message();
      replyTo = new HashSet<>();
    }


    public Builder to(String emailAddress) {
      return to(Collections.singletonList(emailAddress));
    }

    public Builder to(Collection<String> emailAddresses) {
      ArrayList<String> toAddresses = new ArrayList<>(emailAddresses);
      this.destination = new Destination(toAddresses);
      return this;
    }

    public Builder from(String from) {
      this.source = from;
      return this;
    }

    public Builder body(String bodyText) {
      Content content = new Content().withData(bodyText).withCharset("UTF-8");
      Body body = new Body().withHtml(content);
      message.setBody(body);
      return this;
    }

    public Builder body(Body body) {
      message.setBody(body);
      return this;
    }

    public Builder replyTo(String replyTo) {
      this.replyTo.add(replyTo);
      return this;
    }

    public Builder replyTo(Collection<String> replyTo) {
      this.replyTo.addAll(replyTo);
      return this;
    }

    public Builder subject(String subject) {
      Content content = new Content(subject);
      message.setSubject(content);
      return this;
    }

    public EmailRequest build() {
      return new EmailRequest(this);
    }
  }

}
