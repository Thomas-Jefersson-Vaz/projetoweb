package com.mikrolabs.Servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.mikrolabs.DTO.LocationDTO;
import com.mikrolabs.JwtUtil;
import com.mikrolabs.controllers.CookieUtil;
import com.mikrolabs.entities.Location;
import com.mikrolabs.services.LocationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchServlet extends BaseServlet<LocationService> {

    public SearchServlet() {
        super(new LocationService());
    }

    /**
     * GET /search → serve a página HTML de busca
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String token = CookieUtil.getJwt(req);

            if (token == null || !JwtUtil.isValid(token)) {
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                resp.setContentType("text/html");
                req.getRequestDispatcher("/search/index.html").forward(req, resp);
            }

        } catch (IOException | ServletException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.getWriter().println("{\"status\": false, \"mensagem\": \"Ocorreu um erro no servidor.\", \"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String token = CookieUtil.getJwt(req);

//            if (token == null || !JwtUtil.isValid(token)) {
//                resp.sendRedirect(req.getContextPath() + "/login");
//            } else {
//                resp.setContentType("text/html");
//                req.getRequestDispatcher("/search/index.html").forward(req, resp);
//            }

            String name = req.getParameter("name");
            String start = req.getParameter("startDate");
            String end = req.getParameter("endDate");

            LocalDate startDate = (start != null && !start.isBlank()) ? LocalDate.parse(start) : null;
            LocalDate endDate = (end != null && !end.isBlank()) ? LocalDate.parse(end) : null;


            List<LocationDTO> locations = service.search(name, startDate, endDate).stream().map(Location::toDTO).toList();

            // O enviarSucesso já encapsula a lista dentro do ApiResponse<T>
            enviarSucesso(resp, "Busca realizada com sucesso", locations);

        } catch (Exception e) {
            // O enviarErro já encapsula a mensagem de erro no padrão ApiResponse
            enviarErro(resp, 500, "Erro ao processar busca: " + e.getMessage());
        }
    }
}
