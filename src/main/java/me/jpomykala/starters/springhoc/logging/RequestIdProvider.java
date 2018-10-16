package me.jpomykala.starters.springhoc.logging;

import javax.servlet.http.HttpServletRequest;

public interface RequestIdProvider {

  String getRequestId(HttpServletRequest request);
}
