package sensorsus_02.jpa;

import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServicoJpqlTest extends GenericTest {
    
    @Test
    public void servicoPorId() {
        logger.info("Executando servicoPorId()");
        TypedQuery<Servico> query = em.createQuery("SELECT s FROM Servico s WHERE s.id = ?1", 
                Servico.class);
        Long id = 4L;
        query.setParameter(1, id);
        Servico servico = query.getSingleResult();
        assertEquals(id, servico.getId());
        assertEquals("Vacinação", servico.getNome());
        assertEquals("Enfermagem", servico.getDepartamento());
    }
    
    @Test
    public void servicoPorNome() {
        logger.info("Executando serivocPorNome()");
        TypedQuery<Servico> query = em.createNamedQuery("Servico.PorNome", Servico.class);
        query.setParameter("nome", "Café da Manhã");
        Servico servico = query.getSingleResult();
        Long id = 5L;
        assertEquals(id, servico.getId());
        assertEquals("Café da Manhã", servico.getNome());
        assertEquals("Cozinha", servico.getDepartamento());
    }
}
