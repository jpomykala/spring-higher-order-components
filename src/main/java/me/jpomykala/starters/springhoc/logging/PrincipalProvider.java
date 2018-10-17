package me.jpomykala.starters.springhoc.logging;

import javax.servlet.http.HttpServletRequest;

public interface PrincipalProvider {

  String getPrincipal(HttpServletRequest request);
}
