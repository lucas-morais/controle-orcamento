package com.github.lucasmorais.controleorcamento.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class RegistroJaExisteExeception extends HttpClientErrorException {

  private static final long serialVersionUID = 1L;

  public RegistroJaExisteExeception(String mensagem) {
    super(HttpStatus.CONFLICT, mensagem);

  }

}
