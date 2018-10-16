package me.jpomykala.starters.springhoc.logging;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoggingFilter extends OncePerRequestFilter {

  private String mdcKey;
  private String mdcLogFormat;
  private PrincipalProvider principalProvider;
  private RequestIdProvider requestIdProvider;

  public LoggingFilter(String mdcKey, String mdcLogFormat) {
    this.mdcKey = mdcKey;
    this.mdcLogFormat = mdcLogFormat;
  }

  public void setPrincipalProvider(PrincipalProvider principalProvider) {
    this.principalProvider = principalProvider;
  }

  public void setRequestIdProvider(RequestIdProvider requestIdProvider) {
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
