package sensorsus_02.jpa;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
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
        profissionalSaude.setLogin("jsac6");
        profissionalSaude.setNome("João da Silva");
        profissionalSaude.setEmail("joao@gmail.com");
        profissionalSaude.setSenha("#1234567890#");
        Calendar c = Calendar.getInstance();
        c.set(1984, Calendar.SEPTEMBER, 24, 0, 0, 0);
        profissionalSaude.setDataNascimento(c.getTime());

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setComentario("Teste avaliação");

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setCodigoCnes("111112222233333");
        estabelecimento.setNome("UPA Teste");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Logradouro teste");
        endereco.setNumero(01);
        endereco.setComplemento("Complemento teste");
        endereco.setBairro("Bairro teste");
        endereco.setCidade("Cidade teste");
        endereco.setEstado("Pernambuco");

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
        ProfissionalSaude profissionalSaude = em.find(ProfissionalSaude.class, 5L);
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
        Long id = 5L;

        ProfissionalSaude profissionalSaude = em.find(ProfissionalSaude.class, id);
        profissionalSaude.setNome("João");
        profissionalSaude.setEmail("joão@mail.com");
        profissionalSaude.setLogin("jpr7");
        profissionalSaude.setSenha("joao1234");

        em.flush();
        
        String jpql = "SELECT ps FROM ProfissionalSaude ps WHERE ps.id = ?1";
        TypedQuery<ProfissionalSaude> query = em.createQuery(jpql, ProfissionalSaude.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        
        assertEquals("João", profissionalSaude.getNome());
        assertEquals("joão@mail.com", profissionalSaude.getEmail());
        assertEquals("jpr7", profissionalSaude.getLogin());
        assertEquals("joao1234", profissionalSaude.getSenha());

    }
    
    @Test
    public void atualizarProfissionalSaudeMerge() {
        Long id = 5L;
        
        ProfissionalSaude profissionalSaude = em.find(ProfissionalSaude.class, id);
        assertNotNull(profissionalSaude);
        profissionalSaude.setNome("Laura");
        profissionalSaude.setEmail("laura@mail.com");
        profissionalSaude.setLogin("lab12");
        profissionalSaude.setSenha("laura1234");
        
        em.clear();
        em.merge(profissionalSaude);
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        profissionalSaude = em.find(ProfissionalSaude.class, id, properties);
        profissionalSaude = em.find(ProfissionalSaude.class, id);
        assertEquals("Laura", profissionalSaude.getNome());
        assertEquals("laura@mail.com", profissionalSaude.getEmail());
        assertEquals("lab12", profissionalSaude.getLogin());
        assertEquals("laura1234", profissionalSaude.getSenha());

    }

    @Test
    public void removerProfissionalSaude() {
        ProfissionalSaude profissionalSaude = em.find(ProfissionalSaude.class, 5L);
        em.remove(profissionalSaude);
        profissionalSaude = em.find(ProfissionalSaude.class, 5L);
        assertNull(profissionalSaude);

        Avaliacao avaliacao = em.find(Avaliacao.class, 6L);
        assertNull(avaliacao);

    }
}
