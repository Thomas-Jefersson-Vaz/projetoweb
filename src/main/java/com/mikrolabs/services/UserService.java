package com.mikrolabs.services;

import com.mikrolabs.DatabaseManager;
import com.mikrolabs.DAO.UserDAO;
import com.mikrolabs.entities.User;

public class UserService {

    private final UserDAO userDAO = DatabaseManager.getDAO(UserDAO.class);

    public User searchUserByEmail(String email) {
        User user = userDAO.findById("users", "email", email);
        return user;
    }

    public Boolean registerUser(String name, String email, String password) {
        return userDAO.registerUser(name, email, password, "user");
    }

    public Boolean registerUser(String name, String email, String password, String role) {
        return userDAO.registerUser(name, email, password, role);
    }

    public Boolean patchUser(String subject, User userNew) {
        return userDAO.PatchUser(userNew.getAssento(), userNew.getBio(), userNew.getCidade(), userNew.getClasse(),
                userNew.getComida(), userNew.getDataNascimento(), userNew.getMoeda(), userNew.getNacionalidade(),
                userNew.getName(), userNew.getNumTelefone(), subject);
    }

}
