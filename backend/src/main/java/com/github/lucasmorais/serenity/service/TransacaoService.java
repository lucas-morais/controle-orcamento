package com.github.lucasmorais.serenity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.lucasmorais.serenity.dto.CriaTransacaoDTO;
import com.github.lucasmorais.serenity.dto.TransacaoDTO;
import com.github.lucasmorais.serenity.model.Receita;
import com.github.lucasmorais.serenity.model.Transacao;
import com.github.lucasmorais.serenity.repository.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    public TransacaoDTO criaTransacao(CriaTransacaoDTO criaTransacaoDTO) {
        Receita receita = new Receita(criaTransacaoDTO);
        Transacao transacao = this.repository.save(receita);
        return new TransacaoDTO(transacao);
    }
}
