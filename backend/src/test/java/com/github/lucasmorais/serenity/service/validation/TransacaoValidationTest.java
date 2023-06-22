package com.github.lucasmorais.serenity.service.validation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.lucasmorais.serenity.builder.TransacaoBuilder;
import com.github.lucasmorais.serenity.dto.CriaTransacaoDTO;
import com.github.lucasmorais.serenity.exception.TransacaoJaExisteException;
import com.github.lucasmorais.serenity.model.TipoTransacao;
import com.github.lucasmorais.serenity.repository.TransacaoRepository;


@ExtendWith(MockitoExtension.class)
public class TransacaoValidationTest {
    
    @InjectMocks
    private TransacaoValidation validation;
    
    @Mock
    private TransacaoRepository repository;

    private LocalDate dataPadrao = LocalDate.parse("2023-10-01");

    @Test
    @DisplayName("Se a transação tiver mesma descrição, mês e tipo de outra transacao no banco de dados, deve lancar exceção")
    void lancaExcecaoSeTransacaoJaFoiDefinida() {
        CriaTransacaoDTO receita = new TransacaoBuilder().data(dataPadrao).buildCriaTransacaoDto();
        TipoTransacao tipoTransacao = TipoTransacao.RECEITA;
        
        when(repository.existsByDescricaoAndDataMonthAndTipoTransacao(
                anyString(), anyInt(), any())).thenReturn(true);
        assertThatThrownBy(() -> this.validation.existsTransacaoDefinida(receita, TipoTransacao.RECEITA))
            .isInstanceOf(TransacaoJaExisteException.class)
            .hasMessageContaining( tipoTransacao.getNome() + " ja existe");
        
    }

    @Test
    @DisplayName("Se a transação tiver mesma descrição, mês e tipo diferente de outra transacão no banco de dados, nada deve acontecer")
    void nadaAconteceSeTransacaoJaFoiDefinidaMasTemTipoDiferente() {
        CriaTransacaoDTO receita = new TransacaoBuilder().data(dataPadrao).buildCriaTransacaoDto();

        when(repository.existsByDescricaoAndDataMonthAndTipoTransacao(
                anyString(), anyInt(), any())).thenReturn(false);
        assertThatCode(() -> this.validation.existsTransacaoDefinida(receita, TipoTransacao.RECEITA))
            .doesNotThrowAnyException();

    }
}
