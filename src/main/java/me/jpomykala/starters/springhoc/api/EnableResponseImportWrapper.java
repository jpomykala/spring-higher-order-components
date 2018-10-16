package me.jpomykala.starters.springhoc.api;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author Jakub Pomykala on 2/24/18.
 * @project vending-style-metrics
 */
@ControllerAdvice
@ConditionalOnWebApplication
public class EnableResponseImportWrapper extends ResponseDataWrapper {

}
