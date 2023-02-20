package com.jpomykala.springhoc.recaptcha;

public interface ReCaptchaService {

    boolean isValid(String reCaptchaToken);

}
