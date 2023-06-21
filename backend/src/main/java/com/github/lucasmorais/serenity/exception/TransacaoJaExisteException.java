package com.github.lucasmorais.serenity.exception;

public class TransacaoJaExisteException extends RuntimeException {
    public TransacaoJaExisteException(String mensagem) {
        super(mensagem);
    }
}
