package com.github.lucasmorais.serenity.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.lucasmorais.serenity.builder.TransacaoBuilder;
import com.github.lucasmorais.serenity.model.Despesa;
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

    @Test
    @DisplayName("Deveria retornar verdadeiro se já existe uma transação do mesmo tipo cadastrada na data")
    public void retornaVerdadeiroSeTransacaoDoMesmoTipoComMesmaDataEDescricaoJaEstaCadastrada() {
        
        String descricao = "Receita Nova";
        LocalDate data = LocalDate.parse("2023-12-01");
        int mes = data.getMonth().getValue();
        Receita receita = new TransacaoBuilder()
                            .descricao(descricao)
                            .data(data)
                            .buildReceita();
        


        this.repository.save(receita);

        boolean receitaJaExiste = this.repository
            .existsByDescricaoAndDataMonthAndTipoTransacao(descricao, mes, Receita.class);
        boolean despesaNaoExiste = this.repository
            .existsByDescricaoAndDataMonthAndTipoTransacao(descricao, mes, Despesa.class);
        
        assertThat(receitaJaExiste).isTrue();
        assertThat(despesaNaoExiste).isFalse();
    
    }
}
