package com.github.lucasmorais.controleorcamento.dto.receita;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriaReceitaDTO(String descricao, BigDecimal valor, LocalDate data) {

}
