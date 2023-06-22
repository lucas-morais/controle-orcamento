package com.github.lucasmorais.serenity.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriaTransacaoDTO(BigDecimal valor, String descricao, LocalDate data) {

}
