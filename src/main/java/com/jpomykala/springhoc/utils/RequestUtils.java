package com.jpomykala.springhoc.utils;


import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

public final class RequestUtils {

  private RequestUtils() {
    //hidden constructor
    throw new AssertionError("Util class not available for instantiation");
  }

  public static String getClientIP(HttpServletRequest request) {
    String defaultOutput = "127.0.0.1";
    if (request == null) {
      return defaultOutput;
    }

    String xfHeader = request.getHeader("X-Forwarded-For");
    if (StringUtils.hasText(xfHeader)) {
      String[] split = xfHeader.split(",");
      if (split.length > 0) {
        return split[0];
      }
    }

    String remoteAddr = request.getRemoteAddr();
    if (StringUtils.hasText(remoteAddr)) {
      return remoteAddr;
    }
    return defaultOutput;
  }

  public static String getPath(HttpServletRequest request) {
    Optional<HttpServletRequest> optionalRequest = Optional.ofNullable(request);
    return optionalRequest
            .map(HttpServletRequest::getRequestURL)
            .map(StringBuffer::toString)
            .orElse("<no_path>");
  }
}
