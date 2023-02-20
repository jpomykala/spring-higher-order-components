package com.jpomykala.springhoc.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;

public class MailService {

  private final AmazonSimpleEmailService simpleEmailService;

  public MailService(AmazonSimpleEmailService simpleEmailService) {
    this.simpleEmailService = simpleEmailService;
  }

  @EventListener(EmailRequest.class)
  public void onEmailMessageRequest(@NonNull EmailRequest emailRequest) {
    sendEmail(emailRequest);
  }

  private void sendEmail(@NonNull EmailRequest emailRequest) {
    SendEmailRequest sendEmailRequest = new SendEmailRequest()
            .withMessage(emailRequest.getMessage())
            .withSource(emailRequest.getSource())
            .withReplyToAddresses(emailRequest.getReplyTo())
            .withDestination(emailRequest.getDestination());
    simpleEmailService.sendEmail(sendEmailRequest);
  }

}
