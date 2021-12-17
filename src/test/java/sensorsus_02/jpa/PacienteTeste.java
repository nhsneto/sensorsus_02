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

public class PacienteTeste extends Teste {

    @Test
    public void persistirPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNumeroSus("123456");
        paciente.setLogin("paciente1");
        paciente.setNome("João da Silva");
        paciente.setEmail("joao@gmail.com");
        paciente.setSenha("#1234567890#");
        Calendar c = Calendar.getInstance();
        c.set(1984, Calendar.SEPTEMBER, 24, 0, 0, 0);
        paciente.setDataNascimento(c.getTime());

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

        paciente.adicionaAvaliacao(avaliacao);
        avaliacao.setEstabelecimento(estabelecimento);
        estabelecimento.setEndereco(endereco);

        em.persist(paciente);
        em.flush();

        assertNotNull(paciente.getId());
        assertNotNull(avaliacao.getId());
        assertNotNull(estabelecimento.getId());
    }

    @Test
    public void consultarPaciente() {
        Paciente paciente = em.find(Paciente.class, 1L);
        assertNotNull(paciente);
        assertEquals("Jose", paciente.getNome());
        assertEquals("jfs6", paciente.getLogin());
        assertEquals("josefreitas49@gmail.com", paciente.getEmail());
        assertEquals("jose1234", paciente.getSenha());
        assertEquals("111222233334444", paciente.getNumeroSus());
        Calendar c = Calendar.getInstance();
        c.set(1989, Calendar.MAY, 17, 0, 0, 0);
        assertEquals(c.getTime().toString(), paciente.getDataNascimento().toString());

        assertEquals(1, paciente.getAvaliacoes().size());

        paciente.getAvaliacoes().forEach(avaliacao -> {
            assertThat(avaliacao.getComentario().toString(),
                    CoreMatchers.anyOf(
                            startsWith("O atendimento na recepção foi rápido, apesar da fila grande")
                    ));
        });
    }

    @Test
    public void atualizarPaciente() {
        Long id = 1L;

        Paciente paciente = em.find(Paciente.class, id);
        paciente.setNome("João");
        paciente.setEmail("joão@mail.com");
        paciente.setLogin("jpr7");
        paciente.setSenha("joao1234");

        em.flush();
        
        String jpql = "SELECT pa FROM Paciente pa WHERE pa.id = ?1";
        TypedQuery<Estabelecimento> query = em.createQuery(jpql, Estabelecimento.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        
        assertEquals("João", paciente.getNome());
        assertEquals("joão@mail.com", paciente.getEmail());
        assertEquals("jpr7", paciente.getLogin());
        assertEquals("joao1234", paciente.getSenha());
    }
    
    @Test
    public void atualizarPacienteMerge(){
        Long id = 1L;
        Paciente paciente = em.find(Paciente.class, id);
        assertNotNull(paciente);
        paciente.setNome("Laura");
        paciente.setEmail("laura@mail.com");
        paciente.setLogin("lab12");
        paciente.setSenha("laura1234");
        
        em.clear();
        em.merge(paciente);
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        paciente = em.find(Paciente.class, id, properties);
        assertEquals("Laura", paciente.getNome());
        assertEquals("laura@mail.com", paciente.getEmail());
        assertEquals("lab12", paciente.getLogin());
        assertEquals("laura1234", paciente.getSenha());
    }
    
    @Test
    public void removerPaciente() {
        Paciente paciente = em.find(Paciente.class, 1L);
        em.remove(paciente);
        paciente = em.find(Paciente.class, 1L);
        assertNull(paciente);

        Avaliacao avaliacao = em.find(Avaliacao.class, 1L);
        assertNull(avaliacao);

    }
}
