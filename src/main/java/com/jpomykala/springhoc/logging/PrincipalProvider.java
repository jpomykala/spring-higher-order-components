package com.jpomykala.springhoc.logging;

import jakarta.servlet.http.HttpServletRequest;

public interface PrincipalProvider {

  String getPrincipal(HttpServletRequest request);
}
