package com.mikrolabs.Servlets;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.mikrolabs.JwtUtil;
import com.mikrolabs.controllers.CookieUtil;
import com.mikrolabs.controllers.GsonUtil;
import com.mikrolabs.entities.Location;
import com.mikrolabs.services.LocationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/z")
public class LocationManagerServlet extends HttpServlet {

    private final LocationService locationService = new LocationService();
    private final Gson gson = GsonUtil.create();

    // Verification method to check if user is admin (Assuming for now valid token = can access, but could check role)
    private boolean isAuthenticated(HttpServletRequest req) {
        String token = CookieUtil.getJwt(req);
        return token != null && JwtUtil.isValid(token);
    }

    /**
     * GET /admin-locations 
     * Either serves HTML (if Accept: text/html) or returns JSON of ALL locations (if Accept: application/json)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (!isAuthenticated(req)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            if ("application/json".equals(req.getHeader("Accept"))) {
                resp.getWriter().println("{\"error\": \"Unauthorized\"}");
            } else {
                resp.sendRedirect(req.getContextPath() + "/login");
            }
            return;
        }

        String accept = req.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            // Serve JSON list
            try {
                List<Location> locations = locationService.findAll();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().println(gson.toJson(locations));
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            // Serve the HTML page
            resp.setContentType("text/html");
            req.getRequestDispatcher("/admin-locations/index.html").forward(req, resp);
        }
    }

    /**
     * POST /admin-locations
     * Creates a new destination
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isAuthenticated(req)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Location newLoc = gson.fromJson(req.getReader(), Location.class);

            if (newLoc.getName() == null || newLoc.getCountry() == null || newLoc.getPrice() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("{\"error\": \"Missing required fields\"}");
                return;
            }

            boolean success = locationService.insert(newLoc);
            if (success) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().println("{\"status\": true}");
            } else {
                throw new Exception("Falha ao salvar no banco.");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.getWriter().println("{\"status\": false, \"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }

    /**
     * DELETE /admin-locations?id={id}
     * Deletes a destination
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isAuthenticated(req)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            String idStr = req.getParameter("id");
            if (idStr == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            int id = Integer.parseInt(idStr);
            boolean success = locationService.deleteById(id);

            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
