package com.jpomykala.springhoc.recaptcha.google;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableConfigurationProperties(GoogleReCaptchaConfigurationProperties.class)
public class GoogleReCaptchaAutoConfiguration {

    private final GoogleReCaptchaConfigurationProperties properties;


    private final RestTemplate restTemplate;

    public GoogleReCaptchaAutoConfiguration(GoogleReCaptchaConfigurationProperties properties, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring-hoc.recaptcha.google", name = "secret")
    public GoogleReCaptchaService reCaptchaService() {
        return new GoogleReCaptchaService(restTemplate, properties);
    }

}

