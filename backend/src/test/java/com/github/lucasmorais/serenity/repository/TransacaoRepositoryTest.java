package com.github.lucasmorais.serenity.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.lucasmorais.serenity.builder.TransacaoBuilder;
import com.github.lucasmorais.serenity.model.Receita;
import com.github.lucasmorais.serenity.model.Transacao;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class TransacaoRepositoryTest {

    @Autowired
    private TransacaoRepository repository;

    @Test
    @DisplayName("Testa se receita é criada com sucesso")
    void testaSeReceitaECriadaComSucesso() {
        Receita receita = new TransacaoBuilder().buildReceita();
        Transacao receitaCriada = this.repository.save(receita);

        assertThat(receitaCriada).isInstanceOf(Receita.class);
        assertThat(receitaCriada.getId()).isInstanceOf(Long.class);
        assertThat(receitaCriada.getValor()).isEqualTo(receita.getValor());
        assertThat(receitaCriada.getData()).isEqualTo(receita.getData());
        assertThat(receitaCriada.getDescricao()).isEqualTo(receita.getDescricao());
    }
}
