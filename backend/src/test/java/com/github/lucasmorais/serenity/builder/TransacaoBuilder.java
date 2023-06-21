package com.github.lucasmorais.serenity.builder;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.github.lucasmorais.serenity.dto.CriaTransacaoDTO;
import com.github.lucasmorais.serenity.dto.TransacaoDTO;
import com.github.lucasmorais.serenity.model.Despesa;
import com.github.lucasmorais.serenity.model.Receita;
import com.github.lucasmorais.serenity.model.Transacao;

public class TransacaoBuilder {

    private Long id = null;
    private BigDecimal valor = new BigDecimal("100.00");
    private String descricao = "Nova Transação";
    private LocalDate data = LocalDate.now();

    public TransacaoBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TransacaoBuilder valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public TransacaoBuilder descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public TransacaoBuilder data(LocalDate data) {
        this.data = data;
        return this;
    }

    public Transacao buildTransacao() {
        return new Transacao(id, valor, descricao, data);
    }

    public Receita buildReceita() {
        return new Receita(id, valor, descricao, data);
    }

    public Despesa buildDespesa() {
        return new Despesa(id, valor, descricao, data);
    }

    public CriaTransacaoDTO buildCriaTransacaoDto() {
        return new CriaTransacaoDTO(valor, descricao, data);
    }

    public TransacaoDTO buildTransacaoDTO() {
        return new TransacaoDTO(id, valor, descricao, data);
    }
}
