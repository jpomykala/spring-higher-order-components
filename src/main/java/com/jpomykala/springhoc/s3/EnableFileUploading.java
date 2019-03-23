package com.jpomykala.springhoc.s3;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({AmazonS3AutoConfiguration.class, UploadServiceAutoConfiguration.class})
public @interface EnableFileUploading {
}
