package com.mikrolabs.Servlets;

import java.io.IOException;

import com.mikrolabs.JwtUtil;
import com.mikrolabs.controllers.CookieUtil;
import jakarta.servlet.http.Cookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    try {

      String token = CookieUtil.getJwt(req);

      resp.setContentType("text/html");

      if (token == null || !JwtUtil.isValid(token)) {
        resp.sendRedirect("/login");
      } else {

        Cookie cookie = new Cookie("viagem_session_token", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);

        resp.setContentType("application/json");
        resp.getWriter().println("{\"status\": \"logado com sucesso\"}");

        resp.sendRedirect("/login");
      }
      /// } catch (IOException | ) {
    } catch (IOException e) {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.setContentType("application/json");
      resp.getWriter().println(
          "{\"status\": false, \"mensagem\": \"Ocorreu um erro no servidor.\", \"error\": \"" + e.getMessage() + "\"}");
      e.printStackTrace();
    }
  }
}