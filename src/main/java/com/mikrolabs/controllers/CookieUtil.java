package com.mikrolabs.controllers;

import java.util.Arrays;

import com.mikrolabs.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieUtil {
  public static String getJwt(HttpServletRequest req) {
    // Função para pegar a requisição, isola o token e o retorna
    Cookie[] cookies = req.getCookies();
    if (cookies == null)
      return null;

    return Arrays.stream(cookies)
        .filter(c -> c.getName().equals("viagem_session_token"))
        .map(Cookie::getValue)
        .findFirst()
        .orElse(null);
  }

  public static String returnSubject(HttpServletRequest req) {
    String token = getJwt(req);
    String subject = JwtUtil.validateToken(token);
    return subject;
  }
}
