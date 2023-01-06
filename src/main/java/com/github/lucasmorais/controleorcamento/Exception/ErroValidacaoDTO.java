package com.github.lucasmorais.controleorcamento.Exception;

import org.springframework.validation.FieldError;

public record ErroValidacaoDTO(String campo, String Message) {
  public ErroValidacaoDTO(FieldError error) {
    this(error.getField(), error.getDefaultMessage());
  }
}
