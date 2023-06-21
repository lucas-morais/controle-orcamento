package com.github.lucasmorais.serenity.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.lucasmorais.serenity.dto.CriaTransacaoDTO;
import com.github.lucasmorais.serenity.exception.TransacaoJaExisteException;
import com.github.lucasmorais.serenity.model.TipoTransacao;
import com.github.lucasmorais.serenity.repository.TransacaoRepository;

@Component
public class TransacaoValidation {
    
    @Autowired
    private TransacaoRepository repository;
    
    public void existsTransacaoDefinida(CriaTransacaoDTO criaTransacaoDTO, 
    TipoTransacao tipoTransacao) {
        int mes = criaTransacaoDTO.data().getMonthValue();
        if(this.repository.existsByDescricaoAndDataMonthAndTipoTransacao(
            criaTransacaoDTO.descricao(), mes, tipoTransacao.classe)) {
            throw new TransacaoJaExisteException(tipoTransacao.getNome() + " já existe");
        }
    }
}
