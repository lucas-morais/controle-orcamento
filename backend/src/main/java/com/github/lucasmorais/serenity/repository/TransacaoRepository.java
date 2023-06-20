package com.github.lucasmorais.serenity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.lucasmorais.serenity.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("""
        SELECT COUNT(t) > 0 FROM Transacao t
        WHERE t.descricao = :descricao AND MONTH(t.data) = :mes AND TYPE(t) = :tipoTransacao
        """)
    boolean existsByDescricaoAndDataMonthAndTipoTransacao(String descricao, 
    int mes, Class<? extends Transacao> tipoTransacao);

}
