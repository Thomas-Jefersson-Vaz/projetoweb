package com.mikrolabs.Servlets;

import java.io.IOException;
import com.google.gson.Gson;
import com.mikrolabs.JwtUtil;
import com.mikrolabs.controllers.CookieUtil;
import com.mikrolabs.controllers.GsonUtil;
import com.mikrolabs.controllers.UserController;
import com.mikrolabs.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile/user")
public class UserServlet extends HttpServlet {

  UserController userController = new UserController();

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    try {

      String subject = CookieUtil.returnSubject(req);
      User user = userController.buscarUser(subject);

      Gson gson = GsonUtil.create();

      String userJson = gson.toJson(user);

      resp.setStatus(HttpServletResponse.SC_OK);
      resp.setContentType("application/json");
      resp.getWriter().println(userJson);
    } catch (IOException e) {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.setContentType("application/json");
      resp.getWriter().println(
          "{\"status\": false, \"mensagem\": \"Ocorreu um erro no servidor.\",\"error\": \"" + e.getMessage() + "\"}");
      e.printStackTrace();
    }
  }

  protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    try {
      String token = CookieUtil.getJwt(req);

      if (token != null && JwtUtil.isValid(token)) {
        User userNew = userController.newUserPatched(req);
        String subject = CookieUtil.returnSubject(req);
        if (userController.patchUser(subject, userNew)) {
          resp.setStatus(HttpServletResponse.SC_OK);
          resp.setContentType("application/json");
          resp.getWriter().println("{\"status\": \"success\", \"mensagem\": \"Sucesso na atualização.\"}");
        } else {
          resp.setStatus(HttpServletResponse.SC_OK);
          resp.setContentType("application/json");
          resp.getWriter().println("{\"status\": \"error\", \"mensagem\": \"Sucesso na atualização.\"}");
        }

      } else {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setContentType("application/json");
        resp.getWriter().println("{\"status\": error, \"mensagem\": \"Não logado.\"}");
        // resp.getWriter().println("{\"token\": \"" + token + "\"}");
        return;
      }
    } catch (IOException e) {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.setContentType("application/json");
      resp.getWriter().println(
          "{\"status\": error, \"mensagem\": \"Ocorreu um erro no servidor.\",\"error text\": \"" + e.getMessage()
              + "\"}");
      e.printStackTrace();
    }
  }

}
