package com.github.lucasmorais.serenity.model;

public enum TipoTransacao {
    RECEITA(Receita.class),
    DESPESA(Despesa.class);

    public final Class<? extends Transacao> classe;

    public String getNome() {
        return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
    }

    private TipoTransacao(Class<? extends Transacao> classe) {
        this.classe = classe;

    }
}
