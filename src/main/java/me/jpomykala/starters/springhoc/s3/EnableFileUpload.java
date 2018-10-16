package me.jpomykala.starters.springhoc.s3;

import me.jpomykala.starters.springhoc.logging.SpringHocLoggingAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SpringHocS3AutoConfiguration.class})
public @interface EnableFileUpload {
}
