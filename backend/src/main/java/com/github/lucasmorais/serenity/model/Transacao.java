package com.github.lucasmorais.serenity.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.github.lucasmorais.serenity.dto.CriaTransacaoDTO;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "data" })
@Entity
@Table(name = "transacoes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_transacao")
public class Transacao {

    public Transacao(CriaTransacaoDTO criaTransacaoDto) {
        this.valor = criaTransacaoDto.valor();
        this.descricao = criaTransacaoDto.descricao();
        this.data = criaTransacaoDto.data();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private BigDecimal valor;

    private String descricao;

    private LocalDate data;

}
