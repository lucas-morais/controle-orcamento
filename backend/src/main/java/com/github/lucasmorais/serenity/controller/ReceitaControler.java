package com.github.lucasmorais.serenity.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.lucasmorais.serenity.dto.CriaTransacaoDTO;
import com.github.lucasmorais.serenity.dto.TransacaoDTO;
import com.github.lucasmorais.serenity.model.TipoTransacao;
import com.github.lucasmorais.serenity.service.TransacaoService;

@RestController
@RequestMapping("/receitas")
public class ReceitaControler {

    @Autowired
    private TransacaoService service;
    
    private final TipoTransacao tipoTransacao = TipoTransacao.RECEITA;

    @PostMapping
    public ResponseEntity<TransacaoDTO> criaReceita(@RequestBody CriaTransacaoDTO criaTransacaoDTO) {
        TransacaoDTO receitaCriada = this.service.criaTransacao(criaTransacaoDTO, tipoTransacao);
        URI uri = UriComponentsBuilder
            .fromPath("/receitas/{id}")
            .buildAndExpand(receitaCriada.id())
            .toUri();
        return ResponseEntity.created(uri).body(receitaCriada);
    }

    @GetMapping
    public ResponseEntity<List<TransacaoDTO>> listaReceitas() {
        List<TransacaoDTO> receitas = this.service.listaTransacoesPorTipo(tipoTransacao);
        return ResponseEntity.ok(receitas);
    }
}