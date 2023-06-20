package com.github.lucasmorais.serenity.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.github.lucasmorais.serenity.model.Transacao;

public record TransacaoDTO(Long id, BigDecimal valor, String descricao, LocalDate data) {
    public TransacaoDTO(Transacao transacao) {
        this(transacao.getId(), transacao.getValor(), transacao.getDescricao(), transacao.getData());
    }
}
