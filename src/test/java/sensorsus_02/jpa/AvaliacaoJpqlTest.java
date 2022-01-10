package sensorsus_02.jpa;

import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

public class AvaliacaoJpqlTest extends GenericTest {
    
    @Test
    public void avaliacaoPorID() {
        logger.info("Executando avaliacaoPorId()");
        TypedQuery<Avaliacao> query = em.createQuery("SELECT a FROM Avaliacao a WHERE a.id = ?1",
                Avaliacao.class);
        Long id = 2L;
        query.setParameter(1, id);
        Avaliacao avaliacao = query.getSingleResult();
        String comentario = "Fui vacinado muito rapidamente. Profissionais excelentes";
        assertEquals(id, avaliacao.getId());
        assertEquals(comentario, avaliacao.getComentario());
    }
    
}
