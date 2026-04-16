package com.mikrolabs.DTO;

import com.mikrolabs.entities.Book;

import java.time.LocalDate;

public record BookDTO(
    int userId,
    int locationId,
    LocalDate bookedAt
) implements BaseDTO<Book> {
    @Override
    public Book toEntity() {
        return new Book(
            this.userId,
            this.locationId,
            LocalDate.now()
        );
    }
}
