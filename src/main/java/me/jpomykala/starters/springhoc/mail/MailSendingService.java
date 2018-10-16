package me.jpomykala.starters.springhoc.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;

import java.util.Optional;

public class MailSendingService {

  private Logger log = LoggerFactory.getLogger(MailSendingService.class);

  private final SpringHocMailProperties springHocMailProperties;
  private final AmazonSimpleEmailService simpleEmailService;

  public MailSendingService(SpringHocMailProperties springHocMailProperties, AmazonSimpleEmailService simpleEmailService) {
    this.springHocMailProperties = springHocMailProperties;
    this.simpleEmailService = simpleEmailService;
  }

  @EventListener(EmailMessageRequest.class)
  public void onEmailMessageRequest(@NonNull EmailMessageRequest emailRequest) {
    SendEmailRequest sendEmailRequest = new SendEmailRequest()
            .withMessage(emailRequest.getMessage())
            .withReplyToAddresses(emailRequest.getReplyTo())
            .withDestination(emailRequest.getDestination());
    sendMailMessage(sendEmailRequest);
  }

  private void sendMailMessage(@NonNull SendEmailRequest emailRequest) {
    String subject = Optional.ofNullable(emailRequest)
            .map(SendEmailRequest::getMessage)
            .map(Message::getSubject)
            .map(Content::getData)
            .orElse("");

    Destination destination = emailRequest.getDestination();
    log.debug("Send email {} to {}", subject, destination);
    emailRequest.setSource(springHocMailProperties.getSenderEmailAddress());
    simpleEmailService.sendEmail(emailRequest);
  }
}
