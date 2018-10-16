package me.jpomykala.starters.springhoc.mail;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;

import java.util.*;

/**
 * @author Jakub Pomykala on 4/2/18.
 * @project spring-hoc
 */
public class EmailMessageRequest {

  private Destination destination;
  private Set<String> replyTo;
  private Message message;

  public Destination getDestination() {
    return destination;
  }

  public Set<String> getReplyTo() {
    return replyTo;
  }

  public Message getMessage() {
    return message;
  }

  protected EmailMessageRequest(Builder builder) {
    this.destination = builder.destination;
    this.message = builder.message;
    this.replyTo = new HashSet<>(builder.replyTo);
  }


  public static ToStep builder() {
    return new Builder();
  }

  public interface ToStep {

    SubjectStep to(String to);

    SubjectStep to(Collection<String> to);
  }

  public interface SubjectStep {

    SubjectStep replyTo(String replyTo);

    SubjectStep replyTo(Collection<String> replyTo);

    BodyStep subject(String subject);
  }

  public interface BodyStep {

    Builder body(String body);
  }

  public static class Builder implements ToStep, SubjectStep, BodyStep {

    private Message message;
    private Destination destination;
    private Collection<String> replyTo;

    Builder() {
      message = new Message();
      replyTo = new ArrayList<>();
    }

    @Override
    public SubjectStep to(String address) {
      return to(Collections.singletonList(address));
    }

    @Override
    public SubjectStep to(Collection<String> to) {
      ArrayList<String> toAddresses = new ArrayList<>(to);
      this.destination = new Destination(toAddresses);
      return this;
    }

    @Override
    public Builder body(String bodyText) {
      Content content = new Content().withData(bodyText).withCharset("UTF-8");
      Body body = new Body().withHtml(content);
      message.setBody(body);
      return this;
    }

    @Override
    public SubjectStep replyTo(String replyTo) {
      this.replyTo.add(replyTo);
      return this;
    }

    @Override
    public SubjectStep replyTo(Collection<String> replyTo) {
      this.replyTo.addAll(replyTo);
      return this;
    }

    @Override
    public Builder subject(String subject) {
      Content content = new Content(subject);
      message.setSubject(content);
      return this;
    }

    public EmailMessageRequest build() {
      return new EmailMessageRequest(this);
    }
  }

}
