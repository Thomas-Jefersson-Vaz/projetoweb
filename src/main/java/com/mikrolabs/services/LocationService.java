package com.mikrolabs.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.mikrolabs.DatabaseManager;
import com.mikrolabs.DAO.LocationDAO;
import com.mikrolabs.entities.Location;

public class LocationService {

    private final LocationDAO locationDAO = DatabaseManager.getDAO(LocationDAO.class);

    /**
     * Retorna todos os destinos
     */
    public List<Location> findAll() {
        return locationDAO.findAll();
    }

    /**
     * Busca destinos pelo nome (parcial, case-insensitive)
     */
    public List<Location> search(String name, LocalDate start, LocalDate end) {
        return locationDAO.searchComplex(name, start, end);
    }

    /**
     * Busca destinos pelo continente
     */
    public List<Location> findByContinent(String continent) {
        return locationDAO.findByContinent(continent);
    }

    /**
     * Adiciona um novo destino
     */
    public boolean insert(Location loc) {
        return locationDAO.insert(loc.getName(), loc.getCountry(), loc.getContinent(),
                                  loc.getDescription(), loc.getPrice(), loc.getImageUrl(),
                                  loc.getStartDate(), loc.getEndDate());
    }

    /**
     * Exclui um destino pelo id
     */
    public boolean deleteById(int id) {
        return locationDAO.deleteById(id);
    }
}
