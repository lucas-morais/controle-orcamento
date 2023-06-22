package com.github.lucasmorais.serenity.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.time.LocalDate;

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
import com.github.lucasmorais.serenity.dto.TransacaoDTO;
import com.github.lucasmorais.serenity.model.TipoTransacao;
import com.github.lucasmorais.serenity.service.TransacaoService;

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

    @MockBean
    private TransacaoService service;

    private LocalDate dataPadrao = LocalDate.parse("2023-12-01");

    private TipoTransacao tipoTransacao = TipoTransacao.RECEITA;


    @Test
    @DisplayName("Cria receita com sucesso, retornando objeto JSON e Status Code 201")
    void deveCriarReceita() throws IOException, Exception {
        var criaReceitaDto = new TransacaoBuilder().data(dataPadrao).buildCriaTransacaoDto();
        var receitaCriada = new TransacaoBuilder().id(1L).data(dataPadrao).buildTransacaoDTO();

        when(this.service.criaTransacao(criaReceitaDto, tipoTransacao)).thenReturn(receitaCriada);

        var response = mockMvc.perform(
            post("/receitas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(criaReceitaJson.write(criaReceitaDto).getJson())
            ).andReturn().getResponse();

        var josnEsperado = receitaJson.write(receitaCriada).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(josnEsperado);
    }
}
