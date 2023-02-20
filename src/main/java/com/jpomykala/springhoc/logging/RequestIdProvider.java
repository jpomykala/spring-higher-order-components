package com.jpomykala.springhoc.logging;

import jakarta.servlet.http.HttpServletRequest;

public interface RequestIdProvider {

  String getRequestId(HttpServletRequest request);
}
