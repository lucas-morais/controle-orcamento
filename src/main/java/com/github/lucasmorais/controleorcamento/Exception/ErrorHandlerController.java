package com.github.lucasmorais.controleorcamento.Exception;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorHandlerController {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErroValidacaoDTO>> TrataErroValidacao(
      MethodArgumentNotValidException ex) {
    List<FieldError> fieldErrors = ex.getFieldErrors();
    return ResponseEntity.badRequest()
        .body(fieldErrors.stream().map(ErroValidacaoDTO::new).toList());
  }

  @ExceptionHandler(HttpMessageConversionException.class)

  public ResponseEntity<ErroDTO> trataErroDadosIncorretos() {
    return ResponseEntity.badRequest().body(new ErroDTO("Dados Inválidos"));
  }

  @ExceptionHandler(RegistroJaExisteExeception.class)
  public ResponseEntity<ErroDTO> trataErroDadosConflitantes(RegistroJaExisteExeception ex) {
    return ResponseEntity.status(ex.getStatusCode()).body(new ErroDTO(ex.getMessage()));
  }


  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErroDTO> trataErroRegistroNaoEncontrado() {
    return ResponseEntity.notFound().build();
  }

}
