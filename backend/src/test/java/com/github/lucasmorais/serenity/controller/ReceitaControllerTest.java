package com.github.lucasmorais.serenity.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.github.lucasmorais.serenity.builder.TransacaoBuilder;
import com.github.lucasmorais.serenity.dto.CriaTransacaoDTO;
import com.github.lucasmorais.serenity.dto.ErroDTO;
import com.github.lucasmorais.serenity.dto.TransacaoDTO;
import com.github.lucasmorais.serenity.exception.TransacaoJaExisteException;
import com.github.lucasmorais.serenity.model.TipoTransacao;
import com.github.lucasmorais.serenity.service.TransacaoService;

import jakarta.persistence.EntityNotFoundException;

@WebMvcTest(ReceitaControler.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ReceitaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private JacksonTester<CriaTransacaoDTO> criaReceitaJson;

    @Autowired
    private JacksonTester<TransacaoDTO> receitaJson;


    @Autowired
    private JacksonTester<ErroDTO> erroJson;
    
    @Autowired 
    private JacksonTester<List<TransacaoDTO>> listaDeReceitasJson;

    @MockBean
    private TransacaoService service;

    private LocalDate dataPadrao = LocalDate.parse("2023-12-01");

    private TipoTransacao tipoTransacao = TipoTransacao.RECEITA;


    @Test
    @DisplayName("Cria receita com sucesso, retornando objeto JSON e Status Code 201")
    void deveCriarReceita() throws IOException, Exception {
        Long id = 10L;
        var criaReceitaDto = new TransacaoBuilder().data(dataPadrao).buildCriaTransacaoDto();
        var receitaCriada = new TransacaoBuilder().id(id).data(dataPadrao).buildTransacaoDTO();

        when(this.service.criaTransacao(criaReceitaDto, tipoTransacao)).thenReturn(receitaCriada);

        var response = mockMvc.perform(
            post("/receitas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(criaReceitaJson.write(criaReceitaDto).getJson())
            ).andReturn().getResponse();

        var josnEsperado = receitaJson.write(receitaCriada).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader("Location")).isEqualTo("/receitas/" + 10);
        assertThat(response.getContentAsString()).isEqualTo(josnEsperado);
    }

    @Test
    @DisplayName("Deve retornar status 409 e mensagem de erro ao tentar criar receita já existente")
    void deveRetornarMensagemDeErroAoTeentarCriarReceitaJaDefinida() throws IOException, Exception {
        var criaReceitaDto = new TransacaoBuilder().data(dataPadrao).buildCriaTransacaoDto();

        when(this.service.criaTransacao(any(CriaTransacaoDTO.class), any(TipoTransacao.class)))
            .thenThrow(new TransacaoJaExisteException("Receita ja existe"));

        var response = mockMvc.perform(
            post("/receitas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(criaReceitaJson.write(criaReceitaDto).getJson())
            ).andReturn().getResponse();

        var jsonEsperado = erroJson
            .write(new ErroDTO(HttpStatus.CONFLICT.value(), "Receita ja existe"))
            .getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("deve retornar status 200 e uma lista de receitas")
    void listaReceitas() throws Exception {
        List<TransacaoDTO> listaDeReceitas = ciraListaDeReceitas();

        when(this.service.listaTransacoesPorTipo(any(TipoTransacao.class))).thenReturn(listaDeReceitas);

        var jsonEsperado = listaDeReceitasJson.write(listaDeReceitas).getJson();

        var response = mockMvc
            .perform(get("/receitas"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);


    }

    @Test
    @DisplayName("Deve retornar status 404 e mensagem de erro ao tentar acessar recurso que não existe")
    void deveRetornarMensagemDeErroAoTentarAcessarTransacaoInexistente() throws Exception {
        
        when(this.service.detalhaTransacao(anyLong()))
            .thenThrow(new EntityNotFoundException());
        
        var jsonEsprado = erroJson.write(new ErroDTO(404, "Recurso inexistente")).getJson();
        
        var response = mockMvc
            .perform(get("/receitas/{id}", 1))
            .andReturn()
            .getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsprado);
    }

     @Test
    @DisplayName("Deve retornar status 200 e receita detalhada")
    void deveRetornarReceitaDetalhada() throws Exception {
        Long id = 1L;
        TransacaoDTO transacao = new TransacaoBuilder().id(id).buildTransacaoDTO();
        
        when(this.service.detalhaTransacao(id)).thenReturn(transacao);
        
        var jsonEsprado = receitaJson.write(transacao).getJson();
            
        var response = mockMvc
            .perform(get("/receitas/{id}", 1))
            .andReturn()
            .getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsprado);
    }

    private List<TransacaoDTO> ciraListaDeReceitas() {
        return IntStream.range(1,4).mapToObj( i -> new TransacaoBuilder()
                .id(Long.valueOf(i))
                .descricao("Receita " + i)
                .buildTransacaoDTO()).toList();
    }
    
}
