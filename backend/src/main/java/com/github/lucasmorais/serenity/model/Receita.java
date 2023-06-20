package com.github.lucasmorais.serenity.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.github.lucasmorais.serenity.dto.CriaTransacaoDTO;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("R")
public class Receita extends Transacao {
    public Receita() {}

    public Receita(Long id, BigDecimal valor, String descricao, LocalDate data) {
        super(id, valor, descricao, data);
    }
    public Receita(CriaTransacaoDTO criaTransacaoDTO) {
        super(criaTransacaoDTO);
    }
}
