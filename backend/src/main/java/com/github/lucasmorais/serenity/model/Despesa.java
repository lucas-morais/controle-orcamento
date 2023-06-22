package com.github.lucasmorais.serenity.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("D")
public class Despesa extends Transacao {

    public Despesa(Long id, BigDecimal valor, String descricao, LocalDate data) {
    }
    
}
