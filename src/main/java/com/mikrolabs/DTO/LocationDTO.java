package com.mikrolabs.DTO;

import com.mikrolabs.entities.Location;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LocationDTO(
    int id,
    String name,
    String country,
    String continent,
    String description,
    BigDecimal price,
    String imageUrl,
    LocalDate startDate,
    LocalDate endDate
) implements BaseDTO<Location> {

    @Override
    public Location toEntity() {
        return new Location(
            this.id,
            this.name,
            this.country,
            this.continent,
            price,
            description,
            startDate,
            endDate
        );
    }
}
