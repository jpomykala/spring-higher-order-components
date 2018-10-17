package me.jpomykala.starters.springhoc.api;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EnableResponseImportWrapperAutoConfiguration.class})
public @interface EnableResponseWrapping {

}
