package com.jpomykala.springhoc.wrapper;

import com.amazonaws.util.IOUtils;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestWrapperApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResponseWrappingIT {
  @LocalServerPort
  private int serverPort;

  private HttpClient client = HttpClientBuilder.create().build();

  @Test
  public void shouldWrapEntityResponseWhenConfigured() throws Exception {
    //given
    String url = "http://localhost:" + serverPort + "/echo";
    HttpGet request = new HttpGet(url);

    //when
    HttpResponse response = client.execute(request);

    //then
    String json = IOUtils.toString(response.getEntity().getContent());
    String firstName = JsonPath.read(json, "$.data.firstName");
    Assertions.assertThat(firstName).isEqualTo("Jakub");

    String lastName = JsonPath.read(json, "$.data.lastName");
    Assertions.assertThat(lastName).isEqualTo("Pomykała");

    String message = JsonPath.read(json, "$.msg");
    Assertions.assertThat(message).isEqualTo("OK");

    int status = JsonPath.read(json, "$.status");
    Assertions.assertThat(status).isEqualTo(200);
  }

  @Test
  public void shouldWrapPageResponseWhenConfigured() throws Exception {
    //given
    String url = "http://localhost:" + serverPort + "/paged-echo";
    HttpGet request = new HttpGet(url);

    //when
    HttpResponse response = client.execute(request);

    //then
    String json = IOUtils.toString(response.getEntity().getContent());
    String firstName = JsonPath.read(json, "$.data[0].firstName");
    Assertions.assertThat(firstName).isEqualTo("Jakub");

    String lastName = JsonPath.read(json, "$.data[0].lastName");
    Assertions.assertThat(lastName).isEqualTo("Pomykała");

    String message = JsonPath.read(json, "$.msg");
    Assertions.assertThat(message).isEqualTo("OK");

    int status = JsonPath.read(json, "$.status");
    Assertions.assertThat(status).isEqualTo(200);

    int totalElements = JsonPath.read(json, "$.pageDetails.totalElements");
    Assertions.assertThat(totalElements).isEqualTo(2);
  }

}
