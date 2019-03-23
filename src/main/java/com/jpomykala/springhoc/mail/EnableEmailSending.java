package com.jpomykala.springhoc.mail;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({AmazonSesAutoConfiguration.class, MailServiceAutoConfiguration.class})
public @interface EnableEmailSending {
}
