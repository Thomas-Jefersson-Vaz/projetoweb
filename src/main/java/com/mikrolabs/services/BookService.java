package com.mikrolabs.services;

import com.mikrolabs.DAO.BookDAO;
import com.mikrolabs.DAO.LocationDAO;
import com.mikrolabs.DatabaseManager;
import com.mikrolabs.entities.Book;
import com.mikrolabs.entities.User;

import java.time.LocalDate;

public class BookService {
    private final BookDAO bookDao = DatabaseManager.getDAO(BookDAO.class);

    public void book(int locationId, User user){
        Book booking = new Book(
            user.getId(),
            locationId,
            LocalDate.now()
        );

        bookDao.insert(booking);
    }
}
