package me.jpomykala.starters.springhoc.api;

import org.springframework.http.HttpStatus;

/**
 * Created by evelan on 21/02/2017.
 */
public class RestResponse<T> {

  private String msg;
  private Integer status;
  private T data;
  private PageDetails pageDetails;

  public String getMsg() {
    return msg;
  }

  public Integer getStatus() {
    return status;
  }

  public T getData() {
    return data;
  }

  public PageDetails getPageDetails() {
    return pageDetails;
  }

  public RestResponse(String msg, HttpStatus status, T data, PageDetails pageDetails) {
    this.msg = msg;
    this.status = status.value();
    this.data = data;
    this.pageDetails = pageDetails;
  }

  @SuppressWarnings("unchecked")
  public static RestResponse ok(Object body) {
    return new RestResponse("OK", HttpStatus.OK, body, null);
  }

  @SuppressWarnings("unchecked")
  public static RestResponse ok(Object body, PageDetails pageDetails) {
    return new RestResponse("OK", HttpStatus.OK, body, pageDetails);
  }

}
