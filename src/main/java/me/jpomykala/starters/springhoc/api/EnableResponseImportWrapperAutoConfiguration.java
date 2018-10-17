package me.jpomykala.starters.springhoc.api;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@ConditionalOnWebApplication
public class EnableResponseImportWrapperAutoConfiguration extends ResponseWrapperAutoConfiguration {

}
