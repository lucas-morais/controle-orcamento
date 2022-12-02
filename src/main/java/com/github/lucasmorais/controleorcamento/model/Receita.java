package com.github.lucasmorais.controleorcamento.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import com.github.lucasmorais.controleorcamento.dto.receita.CriarReceitaDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "receitas")
@Entity(name = "Receita")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Receita {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String descricao;

  private BigDecimal valor;

  private LocalDate data = LocalDate.now();


  public Receita(CriarReceitaDTO receita) {
    this.descricao = receita.descricao();
    this.valor = receita.valor();
    this.data = receita.data();
  }
}
