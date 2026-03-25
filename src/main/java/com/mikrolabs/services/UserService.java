package com.mikrolabs.services;

import com.mikrolabs.DatabaseManager;
import com.mikrolabs.DAO.UserDAO;
import com.mikrolabs.entities.User;

public class UserService {

    private final UserDAO userDAO = DatabaseManager.getDAO(UserDAO.class);
    

    public User searchUserByEmail(String email) { //Aqui seria a conexão com o banco de dados para buscar o usuário pelo email
        User user = userDAO.findById("users", "email", email);
        return user;
    }


    public Boolean registerUser(String name, String email, String password) {
        return userDAO.registerUser(name, email, password, "user");
    }

    public Boolean registerUser(String name, String email, String password, String role){
        return userDAO.registerUser(name, email, password, role);
    }
    

 }
