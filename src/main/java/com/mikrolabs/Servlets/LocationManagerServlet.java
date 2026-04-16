package com.mikrolabs.Servlets;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.mikrolabs.controllers.GsonUtil;
import com.mikrolabs.entities.Location;
import com.mikrolabs.services.LocationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/locations")
public class LocationManagerServlet extends BaseServlet<LocationService> {

    private final Gson gson = GsonUtil.create();

    public LocationManagerServlet() {
        super(new LocationService());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (!isAuthenticated(req)) {
            // Se for AJAX/JSON, manda erro 401, se não, redireciona
            String accept = req.getHeader("Accept");
            if (accept != null && accept.contains("application/json")) {
                enviarErro(resp, HttpServletResponse.SC_UNAUTHORIZED, "Não autorizado");
            } else {
                resp.sendRedirect(req.getContextPath() + "/login");
            }
            return;
        }

        String accept = req.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                List<Location> locations = service.findAll();
                enviarSucesso(resp, "Lista carregada", locations);
            } catch (Exception e) {
                enviarErro(resp, 500, e.getMessage());
            }
        } else {
            resp.setContentType("text/html");
            req.getRequestDispatcher("/admin-locations/index.html").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isAuthenticated(req)) {
            enviarErro(resp, HttpServletResponse.SC_UNAUTHORIZED, "Não autorizado");
            return;
        }

        try {
            Location newLoc = gson.fromJson(req.getReader(), Location.class);

            if (newLoc.getName() == null || newLoc.getCountry() == null) {
                enviarErro(resp, HttpServletResponse.SC_BAD_REQUEST, "Campos obrigatórios ausentes");
                return;
            }

            boolean success = service.insert(newLoc);
            if (success) {
                enviarSucesso(resp, "Local criado com sucesso!", null);
            } else {
                enviarErro(resp, 400, "Não foi possível salvar o local.");
            }
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro interno: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isAuthenticated(req)) {
            enviarErro(resp, 401, "Não autorizado");
            return;
        }

        try {
            String idStr = req.getParameter("id");
            if (idStr == null) {
                enviarErro(resp, 400, "ID ausente");
                return;
            }

            boolean success = service.deleteById(Integer.parseInt(idStr));
            if (success) {
                enviarSucesso(resp, "Deletado com sucesso", null);
            } else {
                enviarErro(resp, 404, "Local não encontrado");
            }
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar: " + e.getMessage());
        }
    }
}
