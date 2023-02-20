package com.jpomykala.springhoc.logging;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoggingFilter extends OncePerRequestFilter {

  private final String mdcKey;
  private final String mdcLogFormat;
  private PrincipalProvider principalProvider;
  private RequestIdProvider requestIdProvider;

  public LoggingFilter(String mdcKey, String mdcLogFormat) {
    this.mdcKey = mdcKey;
    this.mdcLogFormat = mdcLogFormat;
  }

  void setPrincipalProvider(PrincipalProvider principalProvider) {
    this.principalProvider = principalProvider;
  }

  void setRequestIdProvider(RequestIdProvider requestIdProvider) {
    this.requestIdProvider = requestIdProvider;
  }

  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain) throws ServletException, IOException {
    try {
      String principal = principalProvider.getPrincipal(request);
      String requestId = requestIdProvider.getRequestId(request);
      String mdcData = String.format(mdcLogFormat, principal, requestId);
      MDC.put(mdcKey, mdcData);
      filterChain.doFilter(request, response);
    } finally {
      MDC.clear();
    }
  }
}
