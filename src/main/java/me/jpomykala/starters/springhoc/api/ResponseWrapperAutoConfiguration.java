package me.jpomykala.starters.springhoc.api;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

public class ResponseWrapperAutoConfiguration implements ResponseBodyAdvice {

  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(
          Object body,
          MethodParameter returnType,
          MediaType selectedContentType,
          Class selectedConverterType,
          ServerHttpRequest request,
          ServerHttpResponse response) {

    if (body instanceof RestResponse) {
      return body;
    }

    if (body instanceof Page) {
      List data = ((Page) body).getContent();
      PageDetails pageDetails = new PageDetails((Page) body);
      return RestResponse.ok(data, pageDetails);
    } else {
      return RestResponse.ok(body);
    }
  }
}
