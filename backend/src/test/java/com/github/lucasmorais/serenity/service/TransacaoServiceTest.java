package com.github.lucasmorais.serenity.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.lucasmorais.serenity.dto.CriaTransacaoDTO;
import com.github.lucasmorais.serenity.dto.TransacaoDTO;
import com.github.lucasmorais.serenity.model.Receita;
import com.github.lucasmorais.serenity.repository.TransacaoRepository;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService service;

    @Mock
    private TransacaoRepository repository;

    @Test
    @DisplayName("Deve acessar o repository, criar uma receita e retornar o DTO da receita criada")
    void criaReceitaComSucesso() {
        CriaTransacaoDTO criaReceitaDto = new CriaTransacaoDTO(new BigDecimal("10.0"), "Nova receita", LocalDate.now());
        Receita receitaCriada = new Receita(1L, new BigDecimal("10.00"), "Nova Receita", LocalDate.now());

        when(repository.save(any(Receita.class))).thenReturn(receitaCriada);

        TransacaoDTO receitaDto = service.criaTransacao(criaReceitaDto);
        assertThat(receitaDto).isExactlyInstanceOf(TransacaoDTO.class);
        assertThat(receitaDto.id()).isEqualTo(receitaCriada.getId());
        assertThat(receitaDto.valor()).isEqualTo(receitaCriada.getValor());
        assertThat(receitaDto.descricao()).isEqualTo(receitaCriada.getDescricao());
        assertThat(receitaDto.data()).isEqualTo(receitaCriada.getData());

    }
}
