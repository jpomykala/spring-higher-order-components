package me.jpomykala.starters.springhoc.api;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Jakub Pomykala on 2/26/18.
 * @project vending-style-metrics
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EnableResponseImportWrapper.class})
public @interface EnableResponseWrapping {

}
