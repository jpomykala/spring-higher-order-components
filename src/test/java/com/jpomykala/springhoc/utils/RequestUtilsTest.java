package com.jpomykala.springhoc.utils;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class RequestUtilsTest {

  @Test
  public void shouldGetClientRemoteAddressWhenExists() {
    //given
    MockHttpServletRequest mockServerHttpRequest = new MockHttpServletRequest();
    mockServerHttpRequest.setServerName("https://example.com");
    mockServerHttpRequest.setRequestURI("/example");
    mockServerHttpRequest.setLocalAddr("1.1.1.1");
    mockServerHttpRequest.setRemoteAddr("2.2.2.2");

    //when
    String clientIP = RequestUtils.getClientIP(mockServerHttpRequest);

    //then
    assertThat(clientIP).isEqualTo("2.2.2.2");
  }

  @Test
  public void shouldGetClientXForwardedForWhenExists() {
    //given
    MockHttpServletRequest mockServerHttpRequest = new MockHttpServletRequest();
    mockServerHttpRequest.setServerName("https://example.com");
    mockServerHttpRequest.setRequestURI("/example");
    mockServerHttpRequest.setLocalAddr("1.1.1.1");
    mockServerHttpRequest.setRemoteAddr("2.2.2.2");
    mockServerHttpRequest.addHeader("X-Forwarded-For", "3.3.3.3");

    //when
    String clientIP = RequestUtils.getClientIP(mockServerHttpRequest);

    //then
    assertThat(clientIP).isEqualTo("3.3.3.3");
  }

  @Test
  public void shouldGetPath() {
    //given
    MockHttpServletRequest mockServerHttpRequest = new MockHttpServletRequest();
    mockServerHttpRequest.setServerName("example.com");
    mockServerHttpRequest.setRequestURI("/example");
    mockServerHttpRequest.setQueryString("param1=a&param2=b");

    //when
    String path = RequestUtils.getPath(mockServerHttpRequest);

    //then
    assertThat(path).isEqualTo("http://example.com/example");
  }

  @Test
  public void shouldReturnDefaultWhenNoIpFound() {
    //given
    MockHttpServletRequest mockServerHttpRequest = new MockHttpServletRequest();
    mockServerHttpRequest.setServerName("https://example.com");
    mockServerHttpRequest.setRequestURI("/example");
    mockServerHttpRequest.setLocalAddr("");
    mockServerHttpRequest.setRemoteAddr("");
    mockServerHttpRequest.addHeader("X-Forwarded-For", "");

    //when
    String clientIP = RequestUtils.getClientIP(mockServerHttpRequest);

    //then
    assertThat(clientIP).isEqualTo("127.0.0.1");
  }

  @Test
  public void shouldReturnLocalhostWhenNoIpFound() {
    //given
    MockHttpServletRequest mockServerHttpRequest = new MockHttpServletRequest();
    mockServerHttpRequest.setServerName("https://example.com");
    mockServerHttpRequest.setRequestURI("/example");

    //when
    String clientIP = RequestUtils.getClientIP(mockServerHttpRequest);

    //then
    assertThat(clientIP).isEqualTo("127.0.0.1");
  }

  @Test
  public void shouldReturnLocalhostWhenNullRequest() {
    //given
    //when
    String clientIP = RequestUtils.getClientIP(null);

    //then
    assertThat(clientIP).isEqualTo("127.0.0.1");
  }
}
