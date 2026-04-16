package com.mikrolabs.entities;

import java.time.LocalDate;

public class Book {
    private int id;
    private int userId;
    private int locationId;
    private LocalDate bookedAt;

    public Book(int id, int userId, int locationId, LocalDate bookedAt) {
        this.id = id;
        this.userId = userId;
        this.locationId = locationId;
        this.bookedAt = bookedAt;
    }

    public Book(int userId, int locationId, LocalDate bookedAt){
        this.userId = userId;
        this.locationId = locationId;
        this.bookedAt = bookedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public void setBookedAt(LocalDate bookedAt) {
        this.bookedAt = bookedAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getLocationId() {
        return locationId;
    }

    public LocalDate getBookedAt() {
        return bookedAt;
    }
}
