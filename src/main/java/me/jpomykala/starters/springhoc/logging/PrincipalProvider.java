package me.jpomykala.starters.springhoc.logging;

import me.jpomykala.starters.springhoc.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;

public interface PrincipalProvider {

  String getPrincipal(HttpServletRequest request);
}
