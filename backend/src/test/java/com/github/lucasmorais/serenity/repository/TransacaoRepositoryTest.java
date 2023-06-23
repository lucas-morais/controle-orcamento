package com.github.lucasmorais.serenity.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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

    @Autowired
    private TestEntityManager em;

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


    @Test
    @DisplayName("Deve retornar todas as as transações do tipo desejado")
    void listaTransacoesPorTipo() {
        ciraListaDeReceitas();

        List<Transacao> receitas = this.repository.findAllByTipoTransacao(Receita.class);
        assertThat(receitas).hasSize(3);
        assertThat(receitas).allMatch(receita -> receita.getClass() == Receita.class);
    }

    @Test
    @DisplayName("Deve retornar transacao buscada pelo id")
    void detalhaTransacao() {
        Receita receita = new TransacaoBuilder()
            .descricao("Receita detalhada")
            .buildReceita();
        Receita receitaCriada = this.repository.save(receita);

        Transacao receitaDetalhada = this.repository.findById(receitaCriada.getId()).get();    
    
        assertThat(receitaDetalhada.getValor()).isEqualTo(receitaCriada.getValor());
        assertThat(receitaDetalhada.getDescricao()).isEqualTo(receitaCriada.getDescricao());
        assertThat(receitaDetalhada.getData()).isEqualTo(receitaCriada.getData());
    }


    private void ciraListaDeReceitas() {
        IntStream.range(1,4).forEach( i -> {
            Receita receitaCriada = new TransacaoBuilder()
                .id(null)
                .descricao("Receita " + i)
                .buildReceita();
            em.persist(receitaCriada);

            Despesa despesaCriada = new TransacaoBuilder()
                .id(null)
                .descricao("Despesa " + i)
                .buildDespesa();
            em.persist(despesaCriada);
        });
    } 
}
