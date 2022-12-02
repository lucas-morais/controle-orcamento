package com.github.lucasmorais.controleorcamento.controller;

import java.net.URI;
import com.github.lucasmorais.controleorcamento.dto.receita.CriarReceitaDTO;
import com.github.lucasmorais.controleorcamento.dto.receita.ListarReceitaDTO;
import com.github.lucasmorais.controleorcamento.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

  @Autowired
  private ReceitaService service;

  @PostMapping
  public ResponseEntity<ListarReceitaDTO> criarReceita(@RequestBody CriarReceitaDTO receita) {
    ListarReceitaDTO receitaDTO = service.criar(receita);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(receitaDTO.id()).toUri();
    return ResponseEntity.status(HttpStatus.CREATED)
        .header(HttpHeaders.LOCATION, location.toString()).body(receitaDTO);
  }
}
