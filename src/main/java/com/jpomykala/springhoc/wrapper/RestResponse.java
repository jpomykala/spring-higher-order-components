package com.jpomykala.springhoc.wrapper;

import org.springframework.http.HttpStatus;

public final class RestResponse<ConcreteResponseData> {

  private final String msg;
  private final int status;
  private final ConcreteResponseData data;
  private final PageDetails pageDetails;

  public String getMsg() {
    return msg;
  }

  public int getStatus() {
    return status;
  }

  public ConcreteResponseData getData() {
    return data;
  }

  public PageDetails getPageDetails() {
    return pageDetails;
  }


  public RestResponse() {
    this.msg = "Internal server error";
    this.status = 500;
    this.data = null;
    this.pageDetails = null;
  }

  public RestResponse(String msg, int status, ConcreteResponseData data, PageDetails pageDetails) {
    this.msg = msg;
    this.status = status;
    this.data = data;
    this.pageDetails = pageDetails;
  }

  private RestResponse(String message, HttpStatus status, ConcreteResponseData data, PageDetails pageDetails) {
    this.msg = message;
    this.status = status.value();
    this.data = data;
    this.pageDetails = pageDetails;
  }

  @SuppressWarnings(value = {"unchecked", "unused"})
  public static RestResponse ok(Object body) {
    return new RestResponse("OK", HttpStatus.OK, body, null);
  }

  @SuppressWarnings(value = {"unchecked", "unused"})
  public static RestResponse ok(Object body, PageDetails pageDetails) {
    return new RestResponse("OK", HttpStatus.OK, body, pageDetails);
  }

  @SuppressWarnings(value = {"unchecked", "unused"})
  public static RestResponse empty(String message, HttpStatus status) {
    return new RestResponse(message, status, null, null);
  }

  @SuppressWarnings(value = {"unchecked", "unused"})
  public static <T> RestResponse of(String message, HttpStatus status, T data) {
    return new RestResponse(message, status, data, null);
  }

  @SuppressWarnings(value = {"unchecked", "unused"})
  public static <T> RestResponse of(String message, HttpStatus status, T data, PageDetails pageDetails) {
    return new RestResponse(message, status, data, pageDetails);
  }

}
