package com.github.lucasmorais.serenity.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("R")
public class Receita extends Transacao {

}
