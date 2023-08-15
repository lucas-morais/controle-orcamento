package com.github.lucasmorais.serenity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.lucasmorais.serenity.dto.ErroDTO;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(TransacaoJaExisteException.class)
    protected ResponseEntity<ErroDTO> trataErrosDeConflito(TransacaoJaExisteException ex) {
        ErroDTO erro = new ErroDTO(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErroDTO> trataErroRecursoNaoExiste(EntityNotFoundException ex) {
        ErroDTO erro = new ErroDTO(HttpStatus.NOT_FOUND.value(), "Recurso inexistente");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }
}
