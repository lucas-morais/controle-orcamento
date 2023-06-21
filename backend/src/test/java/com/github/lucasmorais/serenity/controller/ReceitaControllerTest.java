package com.github.lucasmorais.serenity.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.github.lucasmorais.serenity.dto.CriaTransacaoDTO;
import com.github.lucasmorais.serenity.dto.TransacaoDTO;
import com.github.lucasmorais.serenity.service.TransacaoService;

@WebMvcTest(ReceitaControler.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ReceitaControllerTest {

    @Autowired
    private ReceitaControler receitaController;

    
    @Autowired
    private JacksonTester<CriaTransacaoDTO> criaReceitaDto;

    @Autowired
    private JacksonTester<TransacaoDTO> receitaDto;

    @MockBean
    private TransacaoService service;

    @Test
    @DisplayName("Cria receita com sucesso, retornando objeto JSON e Status Code 201")
    void deveCriarReceita() {

    }
}
