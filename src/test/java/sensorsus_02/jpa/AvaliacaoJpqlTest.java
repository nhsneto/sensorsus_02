package sensorsus_02.jpa;

import java.util.List;
import java.util.regex.Pattern;
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
    
    @Test
    public void avaliacoesSemUsuario() {
        logger.info("Executando avaliacoesSemUsuario()");
        TypedQuery<Avaliacao> query = em.createNamedQuery("Avaliacao.SemUsuario", Avaliacao.class);
        List<Avaliacao> avaliacoes = query.getResultList();
        assertEquals(0, avaliacoes.size());
    }
    
    @Test
    public void avaliacoesPorPadraoEmComentario() {
        logger.info("Executando avaliacoesPorPadraoEmComentario()");
        TypedQuery<Avaliacao> query = em.createQuery("SELECT a FROM Avaliacao a "
                + "WHERE LOWER(a.comentario) LIKE :padrao", Avaliacao.class);
        query.setParameter("padrao", "%fila%");
        List<Avaliacao> avaliacoes = query.getResultList();
        assertEquals(4, avaliacoes.size());
        avaliacoes.forEach(avaliacao -> {
            assertTrue(Pattern.compile("[F|f]ila").matcher(avaliacao.getComentario()).find());
        });
    }
}
