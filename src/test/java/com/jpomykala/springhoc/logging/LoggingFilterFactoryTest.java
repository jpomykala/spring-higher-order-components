package com.jpomykala.springhoc.logging;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class LoggingFilterFactoryTest {

  @Test
  public void shouldCreateFilterFromFactoryWithCustomProviders() throws Exception {
    //given
    LoggingFilterFactory loggingFilterFactory = new LoggingFilterFactory();

    //when
    LoggingFilter filter = loggingFilterFactory
            .withPrincipalProvider(request -> "")
            .withRequestIdProvider(request -> "")
            .withCustomMdc("user", "[u:%s][rid:%s]")
            .createFilter();


    //then
    Assertions.assertThat(filter).isNotNull();
  }

  @Test
  public void shouldCreateFilterFromFactoryWithDefaultProviders() throws Exception {
    //given
    LoggingFilterFactory loggingFilterFactory = new LoggingFilterFactory();

    //when
    LoggingFilter filter = loggingFilterFactory.createFilter();


    //then
    Assertions.assertThat(filter).isNotNull();
  }

  @Test
  public void shouldDoFilterWhenDefaultProviders() throws Exception {
    //given
    LoggingFilterFactory loggingFilterFactory = new LoggingFilterFactory();
    LoggingFilter filter = loggingFilterFactory.createFilter();

    //when
    filter.doFilter(new MockHttpServletRequest(), new MockHttpServletResponse(), new MockFilterChain());

    //then
  }


}
