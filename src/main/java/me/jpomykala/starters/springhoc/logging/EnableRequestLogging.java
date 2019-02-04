package me.jpomykala.starters.springhoc.logging;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({RequestLoggingAutoConfiguration.class})
public @interface EnableRequestLogging {
}
