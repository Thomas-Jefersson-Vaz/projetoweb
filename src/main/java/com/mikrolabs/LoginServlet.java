package com.mikrolabs;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("text/html");

            // InputStream is = getServletContext().getResourceAsStream("/login/index.html");
            // BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            req.getRequestDispatcher("/login/index.html").forward(req, resp);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            String email  = req.getParameter("email");
            String password = req.getParameter("password");

            if (email == null || email.isBlank() || password == null || password.isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("application/json");
                resp.getWriter().println("{\"status\": false, \"mensagem\": \"Email e senha são obrigatórios.\", \"email\": \"" + email + "\", \"password\": \"" + password + "\"}");
                return; 
            } else if (!email.equals("user@example.com") || !password.equals("123456")) { /* depois tem que trocar por validação real */
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.setContentType("application/json");
                resp.getWriter().println("{\"status\": false, \"mensagem\": \"Credenciais inválidas.\"}");
                return;
            } else if (email.equals("user@example.com") && password.equals("123456")) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                resp.getWriter().println("{\"status\": true}");
            } 
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.getWriter().println("{\"status\": false, \"mensagem\": \"Ocorreu um erro no servidor.\", \"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }

}