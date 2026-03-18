package com.mikrolabs.Servlets;

import java.io.IOException;

import com.mikrolabs.Exceptions.UsuarioJaExistenteException;
import com.mikrolabs.controllers.UserController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
    
    UserController userController = new UserController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException  {
        try {
            resp.setContentType("text/html");
            
            req.getRequestDispatcher("/register/index.html").forward(req, resp);

        } catch (IOException | ServletException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.getWriter().println("{\"status\": false, \"mensagem\": \"Ocorreu um erro no servidor.\", \"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email  = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        if (email == null || email.isBlank() || password == null || password.isBlank() || name == null|| name.isBlank()) {
            throw new IllegalArgumentException("Email e senha são obrigatórios.");
        } else {
            try {
                Boolean userCreated = userController.registerUser(email, name, password);
                    if (userCreated) {
                        resp.setStatus(HttpServletResponse.SC_CREATED);
                        resp.setContentType("application/json");
                        resp.getWriter().println("{\"status\": true}");
                    } else {
                        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        resp.setContentType("application/json");
                        resp.getWriter().println("{\"status\": false, \"mensagem\": \"Ocorreu um erro no servidor.\", \"error\":\"Error\"}");
                    }
            } catch (UsuarioJaExistenteException e) {
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                    resp.setContentType("application/json");
                    resp.getWriter().println("{\"status\": Erro, \"mensagem\": \"Usuário já existe.\"}");
            } catch (IOException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().println("{\"status\": false, \"mensagem\": \"Ocorreu um erro no servidor.\", \"error\": \"" + e.getMessage() + "\"}");
                e.printStackTrace();
            } 
            
        } 




    //     try {
    //         String email  = req.getParameter("email");
    //         String password = req.getParameter("password");

    //         if (email == null || email.isBlank() || password == null || password.isBlank()) {
    //         throw new IllegalArgumentException("Email e senha são obrigatórios.");
    //         } else {
    //             try {
    //                 User user = userController.login(email, password);
    //                 if (user != null) {
    //                     resp.setStatus(HttpServletResponse.SC_OK);
    //                     resp.setContentType("application/json");
    //                     resp.getWriter().println("{\"status\": true}");
    //                     resp.sendRedirect(""); // Futura implementação de sessão, por enquanto redireciona para o index.html
    //                 }
    //             } catch (UsuarioNaoEncontrado e) {
    //                 resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    //                 resp.setContentType("application/json");
    //                 resp.getWriter().println("{\"status\": false, \"mensagem\": \"Usuário não encontrado.\"}");
    //                 return;
    //             } catch (SenhaIncorretaException e) {
    //                 resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    //                 resp.setContentType("application/json");
    //                 resp.getWriter().println("{\"status\": false, \"mensagem\": \"Senha incorreta.\"}");
    //                 return;
    //             }

                
    //         }
    //     } catch (IOException e) {
    //         resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    //         resp.setContentType("application/json");
    //         resp.getWriter().println("{\"status\": false, \"mensagem\": \"Ocorreu um erro no servidor.\", \"error\": \"" + e.getMessage() + "\"}");
    //         e.printStackTrace();
    //     }
    }

    
}
