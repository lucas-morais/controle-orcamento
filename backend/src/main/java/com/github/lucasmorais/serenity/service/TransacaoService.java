package com.github.lucasmorais.serenity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.lucasmorais.serenity.dto.CriaTransacaoDTO;
import com.github.lucasmorais.serenity.dto.TransacaoDTO;
import com.github.lucasmorais.serenity.model.Receita;
import com.github.lucasmorais.serenity.model.TipoTransacao;
import com.github.lucasmorais.serenity.model.Transacao;
import com.github.lucasmorais.serenity.repository.TransacaoRepository;
import com.github.lucasmorais.serenity.service.validation.TransacaoValidation;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private TransacaoValidation validation;

    public TransacaoDTO criaTransacao(CriaTransacaoDTO criaTransacaoDTO, TipoTransacao tipoTransacao) {
        validation.existsTransacaoDefinida(criaTransacaoDTO, tipoTransacao);
        Receita receita = new Receita(criaTransacaoDTO);
        Transacao transacao = this.repository.save(receita);
        return new TransacaoDTO(transacao);
    }

    public List<TransacaoDTO> listaPorTransacoesTipo(TipoTransacao tipoTransacao) {
        List<Transacao> listaDeTransacoes = this.repository
            .findAllByTipoTransacao(tipoTransacao.classe);
        return listaDeTransacoes
            .stream()
            .map(transacao -> new TransacaoDTO(transacao))
            .toList();
    }
}
