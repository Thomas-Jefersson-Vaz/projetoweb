package com.mikrolabs.entities;

import com.mikrolabs.DTO.LocationDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Location {
    private int id;
    private String name;
    private String country;
    private String continent;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private LocalDate startDate;
    private LocalDate endDate;

    public Location() {}

    public Location(int id, String name, String country, String continent, String description, BigDecimal price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.continent = continent;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }


    public Location(int id, String name, String country, String continent, BigDecimal price, String description, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.continent = continent;
        this.price = price;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getContinent() { return continent; }
    public void setContinent(String continent) { this.continent = continent; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocationDTO toDTO(){
        return new LocationDTO(
            this.id,
            this.name,
            this.country,
            this.continent,
            this.description,
            this.price,
            this.imageUrl,
            this.startDate,
            this.endDate
        );
    }
}
