package com.mikrolabs.services;

import com.mikrolabs.entities.User;

public class UserService {
    

    public User buscarPorEmail(String email) { //Aqui seria a conexão com o banco de dados para buscar o usuário pelo email
        if (email.equals("thomas.jefersson.vaz@gmail.com")) {
            return new User("thomas.jefersson.vaz@gmail.com", "thomas123", "Thomas Jefersson Vaz", "admin");
        } else if (email.equals("rafael@gmail.com")) {
            return new User("rafael@gmail.com", "rafael123", "Rafael Martins", "user");
        } else { 
            return null;
        }
    }
    

 }
