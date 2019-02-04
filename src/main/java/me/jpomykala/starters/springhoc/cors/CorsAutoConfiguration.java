package me.jpomykala.starters.springhoc.cors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class CorsAutoConfiguration {

  private final CorsProperties properties;

  @Autowired
  public CorsAutoConfiguration(CorsProperties properties) {
    this.properties = properties;
  }

  @Bean
  public FilterRegistrationBean corsFilter() {
    CorsConfiguration configuration = new CorsConfiguration();

    List<String> allowedOrigins = getAllowedOrigins();
    configuration.setAllowedOrigins(allowedOrigins);

    List<String> allowedMethods = getAllowedMethods();
    configuration.setAllowCredentials(true);
    configuration.setAllowedMethods(allowedMethods);

    List<String> allowedHeaders = getAllowedHeaders();
    configuration.setAllowedHeaders(allowedHeaders);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }

  private List<String> getAllowedOrigins() {
    List<String> allowedOrigins = properties.getAllowedOrigins();
    if (allowedOrigins.isEmpty()) {
      return allowedOrigins;
    }
    return Collections.singletonList("*");
  }

  private List<String> getAllowedHeaders() {
    List<String> allowedHeaders = properties.getAllowedHeaders();
    if (!allowedHeaders.isEmpty()) {
      return allowedHeaders;
    }
    return Arrays.asList(
            HttpHeaders.ORIGIN,
            HttpHeaders.REFERER,
            HttpHeaders.USER_AGENT,
            HttpHeaders.CACHE_CONTROL,
            HttpHeaders.CONTENT_TYPE,
            HttpHeaders.ACCEPT,
            HttpHeaders.AUTHORIZATION,
            "X-Requested-With",
            "X-Forwarded-For",
            "x-ijt");
  }

  private List<String> getAllowedMethods() {
    List<String> allowedMethods = properties.getAllowedMethods();
    if (!allowedMethods.isEmpty()) {
      return allowedMethods;
    }
    return Arrays.stream(HttpMethod.values())
            .map(Enum::name)
            .collect(Collectors.toList());
  }

}
