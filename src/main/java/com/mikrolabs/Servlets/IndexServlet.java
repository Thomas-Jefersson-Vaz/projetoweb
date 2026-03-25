package com.mikrolabs.Servlets;

import java.io.IOException;

import com.mikrolabs.JwtUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException  {
        try {
            Cookie[] cookies = req.getCookies();
            String token = null;

            if (cookies != null) {
               for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("viagem_session_token")) {
                        token = cookie.getValue();
                        break;
                    }
                }         
            }

            if (token == null || !JwtUtil.isValid(token)) {
                resp.sendRedirect(req.getContextPath() + "login");
            } else {
                resp.setContentType("text/html");
                req.getRequestDispatcher("/home/index.html").forward(req, resp);
            }
            

        } catch (IOException | ServletException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.getWriter().println("{\"status\": false, \"mensagem\": \"Ocorreu um erro no servidor.\", \"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        } 
    } 
}
