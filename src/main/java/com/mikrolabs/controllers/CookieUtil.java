package com.mikrolabs.controllers;

import java.util.Arrays;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieUtil {
      public static String getJwt(HttpServletRequest req) {
          Cookie[] cookies = req.getCookies();
          if (cookies == null) return null;
  
          return Arrays.stream(cookies)
                  .filter(c -> c.getName().equals("viagem_session_token"))
                  .map(Cookie::getValue)
                  .findFirst()
                  .orElse(null);
      }
  }
