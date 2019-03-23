package com.jpomykala.springhoc.cors;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestCorsApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CorsDefaultConfiguredIT
{
    @LocalServerPort
    private int serverPort;

    private HttpClient client = HttpClientBuilder.create().build();

    @Test
    public void shouldSuccessPreflightWhenDefault() throws Exception
    {
        //given
        String url = "http://localhost:" + serverPort + "/echo";
        HttpOptions request = new HttpOptions(url);
        request.addHeader("Access-Control-Request-Method", "GET");
        request.addHeader("Origin", "http://pornhub.com");

        //when
        HttpResponse response = client.execute(request);

        //then
        int statusCode = response.getStatusLine().getStatusCode();
        Assertions.assertThat(statusCode).isEqualTo(200);
    }

    @Test
    public void shouldSuccessPreflightWhenAnyOrigin() throws Exception
    {
        //given
        String url = "http://localhost:" + serverPort + "/echo";
        HttpOptions request = new HttpOptions(url);
        request.addHeader("Access-Control-Request-Method", "GET");
        request.addHeader("Origin", "http://redtube.com");

        //when
        HttpResponse response = client.execute(request);

        //then
        int statusCode = response.getStatusLine().getStatusCode();
        Assertions.assertThat(statusCode).isEqualTo(200);
    }

}