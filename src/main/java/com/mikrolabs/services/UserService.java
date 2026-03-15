package com.mikrolabs.services;

import com.mikrolabs.DAO.UserDAO;
import com.mikrolabs.entities.User;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    

    public User searchUserByEmail(String email) { //Aqui seria a conexão com o banco de dados para buscar o usuário pelo email
        return userDAO.searchUserByEmail(email).orElse(null);
    }
    

 }
