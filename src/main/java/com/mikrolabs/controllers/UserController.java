package com.mikrolabs.controllers;

import com.mikrolabs.Exceptions.SenhaIncorretaException;
import com.mikrolabs.Exceptions.UsuarioNaoEncontrado;
import com.mikrolabs.entities.User;
import com.mikrolabs.services.UserService;

public class UserController {
    UserService userService = new UserService();

    public User login(String email, String password) {
        User user = userService.searchUserByEmail(email);

        if (user == null) {
            throw new UsuarioNaoEncontrado("Usuário não encontrado.");
        }

        if (!user.getPassword().equals(password)) {
            throw new SenhaIncorretaException("Senha incorreta.");
        }

        return user;
    }
}
