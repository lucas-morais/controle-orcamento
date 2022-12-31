package com.github.lucasmorais.controleorcamento.repository;

import java.time.LocalDate;
import java.util.UUID;
import com.github.lucasmorais.controleorcamento.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, UUID> {

  public Receita findByDescricaoAndDataBetween(String descricao, LocalDate inicio, LocalDate fim);

}
