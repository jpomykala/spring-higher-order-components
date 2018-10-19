package me.jpomykala.starters.springhoc.utils;


import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RequestUtils {

  public static String getClientIP(HttpServletRequest request) {
    String defaultOutput = "UNKNOWN";
    if (request == null) {
      return defaultOutput;
    }

    String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader == null) {
      return request.getRemoteAddr();
    }

    try {
      return xfHeader.split(",")[0];
    } catch (Exception e) {
      return defaultOutput;
    }
  }

  public static String getPath(HttpServletRequest request) {
    Optional<HttpServletRequest> optionalRequest = Optional.ofNullable(request);
    String requestUrl = optionalRequest
            .map(HttpServletRequest::getRequestURL)
            .map(StringBuffer::toString)
            .orElse("<no_path>");
    String queryString = optionalRequest
            .map(HttpServletRequest::getQueryString)
            .orElse("");

    if (queryString.isEmpty()) {
      return requestUrl + "?" + queryString;
    }

    return requestUrl;
  }
}
