package com.github.lucasmorais.serenity.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.lucasmorais.serenity.builder.TransacaoBuilder;
import com.github.lucasmorais.serenity.dto.CriaTransacaoDTO;
import com.github.lucasmorais.serenity.dto.TransacaoDTO;
import com.github.lucasmorais.serenity.exception.TransacaoJaExisteException;
import com.github.lucasmorais.serenity.model.Receita;
import com.github.lucasmorais.serenity.model.TipoTransacao;
import com.github.lucasmorais.serenity.model.Transacao;
import com.github.lucasmorais.serenity.repository.TransacaoRepository;
import com.github.lucasmorais.serenity.service.validation.TransacaoValidation;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService service;

    @Mock
    private TransacaoRepository repository;

    @Mock 
    private TransacaoValidation validation;

    private LocalDate dataPadrao = LocalDate.parse("2023-12-01");

    @Test
    @DisplayName("Deve acessar o repository, criar uma receita e retornar o DTO da receita criada")
    void criaReceitaComSucesso() {
        CriaTransacaoDTO criaReceitaDto = new CriaTransacaoDTO(new BigDecimal("10.0"), "Nova receita", dataPadrao);
        Receita receitaCriada = new Receita(1L, new BigDecimal("10.00"), "Nova Receita", dataPadrao);
        TipoTransacao tipoTransacao = TipoTransacao.RECEITA;
        
        when(repository.save(any(Receita.class))).thenReturn(receitaCriada);

        TransacaoDTO receitaDto = service.criaTransacao(criaReceitaDto, tipoTransacao);
        
        verify(this.validation).existsTransacaoDefinida(any(CriaTransacaoDTO.class), any());
        verify(this.repository).save(any(Transacao.class));
        
        assertThat(receitaDto).isExactlyInstanceOf(TransacaoDTO.class);
        assertThat(receitaDto.id()).isEqualTo(receitaCriada.getId());
        assertThat(receitaDto.valor()).isEqualTo(receitaCriada.getValor());
        assertThat(receitaDto.descricao()).isEqualTo(receitaCriada.getDescricao());
        assertThat(receitaDto.data()).isEqualTo(receitaCriada.getData());

    }

    @DisplayName("Deve lançar exceção, criar uma receita e retornar o DTO da receita criada")
    void lancaExcecaoAOTentarCriarReceitaJaDefinida() {
        CriaTransacaoDTO criaReceitaDto = new CriaTransacaoDTO(new BigDecimal("10.0"), "Nova receita", dataPadrao);
        TipoTransacao tipoTransacao = TipoTransacao.RECEITA;
        
        doThrow(TransacaoJaExisteException.class)
            .when(validation).existsTransacaoDefinida(any(CriaTransacaoDTO.class), any(TipoTransacao.class));
            

        
        assertThatThrownBy(() -> this.service.criaTransacao(criaReceitaDto, tipoTransacao))
            .isInstanceOf(TransacaoJaExisteException.class);
        
        verify(this.repository, never()).save(any(Transacao.class));

    }

    @Test
    @DisplayName("Deve listar apenas transações do tipo especificado")
    void listaTransacoesPorTipo() {
        List<Transacao> receitas = ciraListaDeReceitas();

        when(this.repository.findAllByTipoTransacao(any())).thenReturn(receitas);
        var listaTransacoes = this.service.listaTransacoesPorTipo(TipoTransacao.RECEITA);
        
        assertThat(listaTransacoes).hasSize(3);
        assertThat(listaTransacoes).allMatch(transacao -> transacao.getClass() == TransacaoDTO.class);
    }
    
    private List<Transacao> ciraListaDeReceitas() {
        return IntStream.range(1,4).mapToObj( i -> new TransacaoBuilder()
                .id(Long.valueOf(i))
                .descricao("Receita " + i)
                .buildTransacao()).toList();
    }
    
}
