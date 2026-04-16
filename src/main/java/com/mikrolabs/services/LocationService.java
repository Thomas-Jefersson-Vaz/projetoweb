package com.mikrolabs.services;

import java.time.LocalDate;
import java.util.List;

import com.mikrolabs.DatabaseManager;
import com.mikrolabs.DAO.LocationDAO;
import com.mikrolabs.entities.Location;

public class LocationService {

    private final LocationDAO locationDAO = DatabaseManager.getDAO(LocationDAO.class);

    public List<Location> findAll() {
        return locationDAO.findAll();
    }


    public List<Location> search(String name, LocalDate start, LocalDate end) {
        return locationDAO.searchComplex(name, start, end);
    }

    public List<Location> findByContinent(String continent) {
        return locationDAO.findByContinent(continent);
    }

    public boolean insert(Location loc) {
        return locationDAO.insert(loc.getName(), loc.getCountry(), loc.getContinent(),
                loc.getDescription(), loc.getPrice(), loc.getImageUrl(),
                loc.getStartDate(), loc.getEndDate());
    }

    public boolean deleteById(int id) {
        return locationDAO.deleteById(id);
    }
}
