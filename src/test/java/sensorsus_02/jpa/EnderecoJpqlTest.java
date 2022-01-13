package sensorsus_02.jpa;

import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

public class EnderecoJpqlTest extends GenericTest {
    
    @Test
    public void enderecoPorId() {
        logger.info("Executando enderecoPorId()");
        TypedQuery<Endereco> query = em.createQuery("SELECT e FROM Endereco e WHERE e.id = ?1", 
                Endereco.class);
        Long id = 5L;
        query.setParameter(1, id);
        Endereco endereco = query.getSingleResult();
        assertNotNull(endereco);
        assertEquals(id, endereco.getId());
    }
}
