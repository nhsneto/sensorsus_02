package sensorsus_02.jpa;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import static org.junit.Assert.*;
import org.junit.Test;

public class ServicoTeste extends Teste {
    
    @Test
    public void persistirServico() {
        Servico servico = new Servico();
        servico.setNome("Coleta de sangue");
        servico.setDepartamento("Enfermagem");
        
        em.persist(servico);
        em.flush();
        
        assertNotNull(servico.getId());
    }
    
    @Test
    public void consultarServico() {
        Servico servico = em.find(Servico.class, 2L);
        assertNotNull(servico);
        assertEquals("Atendimento Inicial Ambulatório", servico.getNome());
        assertEquals("Enfermagem", servico.getDepartamento());
    }
    
    @Test
    public void atualizarServico() {
        String novoNome = "Limpeza da Sala de Atendimento ao Paciente";
        Long id = 3L;
        Servico servico = em.find(Servico.class, id);
        servico.setNome(novoNome);
        em.flush();
        String jpql = "SELECT s FROM Servico s WHERE s.id = ?1";
        TypedQuery<Servico> query = em.createQuery(jpql, Servico.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        servico = query.getSingleResult();
        assertEquals(novoNome, servico.getNome());
    }
}
