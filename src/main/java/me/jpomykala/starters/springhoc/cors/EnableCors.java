package me.jpomykala.starters.springhoc.cors;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SpringHocCorsAutoConfiguration.class})
public @interface EnableCors {
}
