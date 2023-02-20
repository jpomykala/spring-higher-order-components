package com.jpomykala.springhoc.recaptcha.google;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring-hoc.recaptcha.google")
public class GoogleReCaptchaConfigurationProperties {
    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
