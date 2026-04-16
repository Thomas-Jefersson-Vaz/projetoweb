package com.mikrolabs.Servlets;

import com.mikrolabs.DAO.UserDAO;
import com.mikrolabs.DatabaseManager;
import com.mikrolabs.controllers.CookieUtil;
import com.mikrolabs.controllers.UserController;
import com.mikrolabs.entities.User;
import com.mikrolabs.services.BookService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/book")
public class BookServlet extends BaseServlet<BookService> {
    private class BookRequest{
        private Integer locationId;
    }
    public BookServlet() {
        super(new BookService());
    }
    private final UserController userController = new UserController();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isAuthenticated(req))  {
            this.enviarErro(resp,400, "Não autorizado");
            return;
        };
        String subject = CookieUtil.returnSubject(req);
        BookRequest data = gson.fromJson(req.getReader(), BookRequest.class);

        if (data != null && data.locationId != null) {
            User user = userController.buscarUser(subject);
            service.book(data.locationId, user);
            this.enviarSucesso(resp, "Criado com sucesso", null);
        } else {
            this.enviarErro(resp, 400, "ID da localização ausente");
        }

    }
}
