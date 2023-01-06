package com.github.lucasmorais.controleorcamento.dto.receita;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriaReceitaDTO(@NotBlank(message = "{descricao.obrigatorio}") String descricao,
    @NotNull @DecimalMin(value = "0.1") BigDecimal valor, @NotNull LocalDate data) {

}
