package com.github.lucasmorais.controleorcamento.dto.receita;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import com.github.lucasmorais.controleorcamento.model.Receita;

public record ListaReceitaDTO(UUID id, String descricao, BigDecimal valor, LocalDate data) {
  public ListaReceitaDTO(Receita receita) {
    this(receita.getId(), receita.getDescricao(), receita.getValor(), receita.getData());
  }
}
