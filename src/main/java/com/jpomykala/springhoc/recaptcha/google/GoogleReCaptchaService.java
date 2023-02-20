package com.jpomykala.springhoc.recaptcha.google;

import com.jpomykala.springhoc.recaptcha.ReCaptchaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class GoogleReCaptchaService implements ReCaptchaService {
    private static final String SITE_VERIFY = "https://www.google.com/recaptcha/api/siteverify";

    private final RestTemplate restTemplate;

    private final GoogleReCaptchaConfigurationProperties properties;

    public GoogleReCaptchaService(RestTemplate restTemplate, GoogleReCaptchaConfigurationProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public ResponseEntity<GoogleReCaptchaResponse> sendReCaptchaRequest(String reCaptchaToken) {
        String reCaptchaSecret = properties.getSecret();
        return restTemplate.postForEntity(SITE_VERIFY + "?secret=" + reCaptchaSecret + "&response=" + reCaptchaToken, null, GoogleReCaptchaResponse.class);

    }

    public boolean isValid(String reCaptchaToken) {
        ResponseEntity<GoogleReCaptchaResponse> reCaptchaResponse = sendReCaptchaRequest(reCaptchaToken);
        GoogleReCaptchaResponse body = reCaptchaResponse.getBody();
        return Optional.ofNullable(body).map(GoogleReCaptchaResponse::isSuccess).orElse(false);

    }

}
