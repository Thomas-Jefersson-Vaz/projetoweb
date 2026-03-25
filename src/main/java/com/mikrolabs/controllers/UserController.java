package com.mikrolabs.controllers;

import com.mikrolabs.Exceptions.SenhaIncorretaException;
import com.mikrolabs.Exceptions.UsuarioNaoEncontrado;
import com.mikrolabs.Exceptions.UsuarioJaExistenteException;
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

    //Registra usuário na tela de registro
    public Boolean registerUser(String email, String name, String password) {
        if (userService.searchUserByEmail(email) != null) {
            throw new UsuarioJaExistenteException("Usuário já existente");
        }

        return userService.registerUser(name, email, password);


    }


}
