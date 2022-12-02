package com.github.lucasmorais.controleorcamento.service;

import com.github.lucasmorais.controleorcamento.dto.receita.CriarReceitaDTO;
import com.github.lucasmorais.controleorcamento.dto.receita.ListarReceitaDTO;
import com.github.lucasmorais.controleorcamento.model.Receita;
import com.github.lucasmorais.controleorcamento.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceitaService {

  @Autowired
  private ReceitaRepository repository;

  public ListarReceitaDTO criar(CriarReceitaDTO receita) {
    Receita receitaCriada = repository.save(new Receita(receita));
    return new ListarReceitaDTO(receitaCriada);
  }
}
