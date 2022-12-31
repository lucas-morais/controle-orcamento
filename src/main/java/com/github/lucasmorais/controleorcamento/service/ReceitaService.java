package com.github.lucasmorais.controleorcamento.service;

import java.time.LocalDate;
import java.time.YearMonth;
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

    YearMonth mes = YearMonth.from(receita.data());

    LocalDate inicio = mes.atDay(1);
    LocalDate fim = mes.atEndOfMonth();

    Receita receitaExiste =
        this.repository.findByDescricaoAndDataBetween(receita.descricao(), inicio, fim);

    if (receitaExiste != null) {
      System.out.println("Já existe");
    }

    Receita saved = this.repository.save(new Receita(receita));

    return new ListarReceitaDTO(saved);

  }
}
