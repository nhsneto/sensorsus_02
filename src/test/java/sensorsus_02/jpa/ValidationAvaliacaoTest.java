package sensorsus_02.jpa;

import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidationAvaliacaoTest extends Teste {
    
    @Test(expected = ConstraintViolationException.class)
    public void persistirAvaliacaoInvalida() {
        Avaliacao avaliacao = null;
        try {
            avaliacao = new Avaliacao();
            avaliacao.setComentario("##############################################################"
                    + "###########################################################################"
                    + "###########################################################################"
                    + "######################################################");
            em.persist(avaliacao);
            em.flush();
        } catch (ConstraintViolationException e) {
            assertEquals(1, e.getConstraintViolations().size());
            assertEquals("O campo comentário deve possuir no máximo 255 caracteres", 
                    e.getConstraintViolations().iterator().next().getMessage());
            assertNull(avaliacao.getId());
            throw e;
        }
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void atualizarAvaliacaoInvalida() {
        String jpql = "SELECT a FROM Avaliacao a WHERE a.usuario.nome = :nome";
        TypedQuery<Avaliacao> query = em.createQuery(jpql, Avaliacao.class);
        query.setParameter("nome", "Amanda");
        Avaliacao avaliacao = query.getSingleResult();
        avaliacao.setComentario("");
        try {
            em.flush();
        } catch (ConstraintViolationException e) {
            assertEquals(1, e.getConstraintViolations().size());
            assertEquals("O campo comentário não pode ser null ou vazio", 
                    e.getConstraintViolations().iterator().next().getMessage());
            throw e;
        }
    }
}
