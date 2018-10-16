package me.jpomykala.starters.springhoc.cors;

import me.jpomykala.starters.springhoc.mail.SpringHocMailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableConfigurationProperties(SpringHocCorsProperties.class)
public class SpringHocCorsAutoConfiguration {

  @Autowired
  private SpringHocCorsProperties corsProperties;

  @Bean
  public FilterRegistrationBean corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    List<String> allowedOrigins = corsProperties.getAllowedOrigins();
    configuration.setAllowedOrigins(allowedOrigins);

    configuration.setAllowCredentials(true);
    configuration.setAllowedMethods(Arrays.asList(
            GET.name(),
            HEAD.name(),
            POST.name(),
            PATCH.name(),
            PUT.name(),
            OPTIONS.name(),
            DELETE.name()));
    List<String> allowedHeaders = Arrays.asList(
            "Origin",
            "Referer",
            "User-Agent",
            "X-Requested-With",
            "X-Forwarded-For",
            "Cache-Control",
            "Content-Type",
            "Accept",
            "Authorization",
            "x-ijt",
            "X-Token");
    configuration.setAllowedHeaders(allowedHeaders);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }

}
