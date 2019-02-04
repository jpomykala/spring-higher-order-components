package me.jpomykala.starters.springhoc.wrapper;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@ControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice {

  @Override
  public boolean supports(MethodParameter methodParameter, Class converterType) {
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
      Page pageBody = (Page) body;
      List data = pageBody.getContent();
      PageDetails pageDetails = PageDetails.of(pageBody);
      return RestResponse.ok(data, pageDetails);
    } else {
      return RestResponse.ok(body);
    }
  }
}
