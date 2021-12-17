package sensorsus_02.jpa;

import java.util.Calendar;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class ProfissionalSaudeTeste extends Teste {

    @Test
    public void persistirProfissionalSaude() {
        ProfissionalSaude profissionalSaude = new ProfissionalSaude();
        profissionalSaude.setInscricaoConselhoRegional("123456");
        profissionalSaude.setLogin("profissionalSaude1");
        profissionalSaude.setNome("João da Silva");
        profissionalSaude.setEmail("joao@gmail.com");
        profissionalSaude.setSenha("#1234567890#");
        Calendar c = Calendar.getInstance();
        c.set(1984, Calendar.SEPTEMBER, 24, 0, 0, 0);
        profissionalSaude.setDataNascimento(c.getTime());

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setComentario("Teste avaliação");

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setCodigoCnes("13456789");
        estabelecimento.setNome("UPA Teste");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Logradouro teste");
        endereco.setNumero(01);
        endereco.setComplemento("Complemento teste");
        endereco.setBairro("Bairro teste");
        endereco.setCidade("Cidade teste");
        endereco.setEstado("Estado teste");

        profissionalSaude.adicionaAvaliacao(avaliacao);
        avaliacao.setEstabelecimento(estabelecimento);
        estabelecimento.setEndereco(endereco);

        em.persist(profissionalSaude);
        em.flush();

        assertNotNull(profissionalSaude.getId());
        assertNotNull(avaliacao.getId());
        assertNotNull(estabelecimento.getId());
    }

    @Test
    public void consultarProfissionalSaude() {
        ProfissionalSaude profissionalSaude = em.find(ProfissionalSaude.class, 2L);
        assertNotNull(profissionalSaude);
        assertEquals("Amanda", profissionalSaude.getNome());
        assertEquals("asrf4", profissionalSaude.getLogin());
        assertEquals("amandasilva20@gmail.com", profissionalSaude.getEmail());
        assertEquals("amanda1234", profissionalSaude.getSenha());
        assertEquals("111222333444", profissionalSaude.getInscricaoConselhoRegional());
        Calendar c = Calendar.getInstance();
        c.set(1999, Calendar.DECEMBER, 21, 0, 0, 0);
        assertEquals(c.getTime().toString(), profissionalSaude.getDataNascimento().toString());

        assertEquals(1, profissionalSaude.getAvaliacoes().size());

        profissionalSaude.getAvaliacoes().forEach(avaliacao -> {
            assertThat(avaliacao.getComentario().toString(),
                    CoreMatchers.anyOf(
                            startsWith("Grande quantidade de pacientes para poucos profissionais de saúde no setor neurológico")
                    ));
        });
    }

    @Test
    public void atualizarProfissionalSaude() {
        Long id = 2L;

        ProfissionalSaude profissionalSaude = em.find(ProfissionalSaude.class, id);
        profissionalSaude.setNome("João");
        profissionalSaude.setEmail("joão@mail.com");
        profissionalSaude.setLogin("jpr7");
        profissionalSaude.setSenha("joao1234");

        em.flush();
        assertEquals("João", profissionalSaude.getNome());
        assertEquals("joão@mail.com", profissionalSaude.getEmail());
        assertEquals("jpr7", profissionalSaude.getLogin());
        assertEquals("joao1234", profissionalSaude.getSenha());

    }

    @Test
    public void removerProfissionalSaude() {
        ProfissionalSaude profissionalSaude = em.find(ProfissionalSaude.class, 2L);
        em.remove(profissionalSaude);
        profissionalSaude = em.find(ProfissionalSaude.class, 2L);
        assertNull(profissionalSaude);

        Avaliacao avaliacao = em.find(Avaliacao.class, 2L);
        assertNull(avaliacao);

    }
}
