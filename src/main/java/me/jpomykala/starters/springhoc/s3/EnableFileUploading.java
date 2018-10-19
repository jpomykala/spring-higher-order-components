package me.jpomykala.starters.springhoc.s3;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SpringHocS3AutoConfiguration.class, SpringHocS3AutoConfiguration.class})
public @interface EnableFileUploading {
}
