package com.jpomykala.springhoc.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {

  @InjectMocks
  private MailService mailService;

  @Mock
  private AmazonSimpleEmailService amazonSimpleEmailService;


  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void shouldOnEmailMessageRequest() throws Exception {
    //given
    EmailRequest emailRequest = EmailRequest.builder()
            .to("jakub.pomykala@gmail.com")
            .subject("Hello")
            .body("Message")
            .build();

    //when
    mailService.onEmailMessageRequest(emailRequest);

    //then
    Mockito.verify(amazonSimpleEmailService).sendEmail(Mockito.any());
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowOnNullRequest() throws Exception {
    //given
    EmailRequest emailRequest = null;

    //when
    mailService.onEmailMessageRequest(emailRequest);

    //then
    Mockito.verifyNoInteractions(amazonSimpleEmailService);
  }
}
