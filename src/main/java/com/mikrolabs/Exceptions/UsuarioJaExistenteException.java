package com.mikrolabs.Exceptions;

public class UsuarioJaExistenteException extends RuntimeException {
  public UsuarioJaExistenteException(String message) {
        super(message);
    }
}
