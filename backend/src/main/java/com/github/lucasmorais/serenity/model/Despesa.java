package com.github.lucasmorais.serenity.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("S")
public class Despesa extends Transacao {
    
}
