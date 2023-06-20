package com.github.lucasmorais.serenity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.lucasmorais.serenity.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
    
}
