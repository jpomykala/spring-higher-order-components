package com.jpomykala.springhoc.wrapper;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

@ControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice {

  @Override
  public boolean supports(MethodParameter methodParameter, Class converterType) {
    Annotation[] methodAnnotations = methodParameter.getMethodAnnotations();

    Type genericParameterType = methodParameter.getGenericParameterType();
    String typeName = genericParameterType.getTypeName();

    if (typeName.contains(ResponseEntity.class.getTypeName())) {
      return false;
    }

    if (typeName.contains(RestResponse.class.getTypeName())) {
      return false;
    }

    for (Annotation methodAnnotation : methodAnnotations) {
      if (methodAnnotation.annotationType().equals(DisableWrapping.class)) {
        return false;
      }
    }
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
