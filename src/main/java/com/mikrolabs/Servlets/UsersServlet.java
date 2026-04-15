package com.mikrolabs.Servlets;

import java.io.IOException;
import java.util.List;

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

@WebServlet("/admin/users")
public class UsersServlet extends HttpServlet {

    private final UserController userController = new UserController();
    private final Gson gson = GsonUtil.create();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("PATCH".equalsIgnoreCase(req.getMethod())) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        try {
            if (!verifyAdmin(req, resp)) return;

            List<User> users = userController.buscarTodosUsuarios();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(gson.toJson(users));

        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no servidor.", e.getMessage());
            e.printStackTrace();
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        try {
            if (!verifyAdmin(req, resp)) return;

            // Target user email from query parameter
            String targetEmail = req.getParameter("email");
            if (targetEmail == null || targetEmail.isBlank()) {
                sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'email' é obrigatório.", null);
                return;
            }

            // Parse updated user data from request body
            User userNew = userController.newUserPatched(req);

            // Update user by target email
            if (userController.patchUser(targetEmail, userNew)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println("{\"status\": \"success\", \"mensagem\": \"Usuário atualizado com sucesso.\"}");
            } else {
                sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar usuário.", null);
            }

        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no servidor.", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Verifies JWT and admin role. Sends error response and returns false if verification fails.
     */
    private boolean verifyAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = CookieUtil.getJwt(req);
        if (token == null || !JwtUtil.isValid(token)) {
            sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Não autenticado.", null);
            return false;
        }

        String subject = CookieUtil.returnSubject(req);
        User currentUser = userController.buscarUser(subject);
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            sendError(resp, HttpServletResponse.SC_FORBIDDEN, "Acesso negado.", null);
            return false;
        }

        return true;
    }

    private void sendError(HttpServletResponse resp, int status, String message, String error) throws IOException {
        resp.setStatus(status);
        String json = "{\"status\": false, \"mensagem\": \"" + message + "\"";
        if (error != null) json += ", \"error\": \"" + error + "\"";
        json += "}";
        resp.getWriter().println(json);
    }
}

