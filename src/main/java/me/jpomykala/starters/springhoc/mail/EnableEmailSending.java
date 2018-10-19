package me.jpomykala.starters.springhoc.mail;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({AmazonSesAutoConfiguration.class, SpringHocMailAutoConfiguration.class})
public @interface EnableEmailSending {
}
