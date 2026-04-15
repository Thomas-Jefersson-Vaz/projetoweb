package com.mikrolabs.controllers;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.mikrolabs.Exceptions.SenhaIncorretaException;
import com.mikrolabs.Exceptions.UsuarioNaoEncontrado;
import com.mikrolabs.Exceptions.UsuarioJaExistenteException;
import com.mikrolabs.entities.User;
import com.mikrolabs.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

public class UserController {
    UserService userService = new UserService();
    Gson gson = GsonUtil.create();

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

    // Registra usuário na tela de registro
    public Boolean registerUser(String email, String name, String password) {
        if (userService.searchUserByEmail(email) != null) {
            throw new UsuarioJaExistenteException("Usuário já existente");
        }

        return userService.registerUser(name, email, password);

    }

    public User buscarUser(String email) throws IOException {
        if (email != null) {
            User user = userService.searchUserByEmail(email);
            user.setPassword(null);
            return user;
        } else
            return null;

    }

    public User newUserPatched(HttpServletRequest req) throws IOException {
        User userNew = gson.fromJson(req.getReader(), User.class);
        return userNew;
    }

    public Boolean patchUser(String subject, User userNew) throws IOException {
        return userService.patchUser(subject, userNew);
    }

    public List<User> buscarTodosUsuarios() {
        List<User> usersList = userService.buscarTodosUsuarios();
        usersList.forEach(user -> user.setPassword(null));

        return usersList;
    }

}
