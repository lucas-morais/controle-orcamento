package com.github.lucasmorais.controleorcamento.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import com.github.lucasmorais.controleorcamento.Exception.RegistroJaExisteExeception;
import com.github.lucasmorais.controleorcamento.dto.receita.CriaReceitaDTO;
import com.github.lucasmorais.controleorcamento.dto.receita.ListaReceitaDTO;
import com.github.lucasmorais.controleorcamento.model.Receita;
import com.github.lucasmorais.controleorcamento.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceitaService {

  @Autowired
  private ReceitaRepository repository;

  public ListaReceitaDTO criar(CriaReceitaDTO receita) {

    YearMonth mes = YearMonth.from(receita.data());

    LocalDate inicio = mes.atDay(1);
    LocalDate fim = mes.atEndOfMonth();

    Receita receitaExiste =
        this.repository.findByDescricaoAndDataBetween(receita.descricao(), inicio, fim);

    if (receitaExiste != null) {
      throw new RegistroJaExisteExeception("receita já cadastrada no mês atual");
    }

    Receita saved = this.repository.save(new Receita(receita));

    return new ListaReceitaDTO(saved);

  }

  public List<ListaReceitaDTO> listar() {
    List<Receita> receitas = this.repository.findAll();
    return receitas.stream().map(ListaReceitaDTO::new).toList();
  }


}
