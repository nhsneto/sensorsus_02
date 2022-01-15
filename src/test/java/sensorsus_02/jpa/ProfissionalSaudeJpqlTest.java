package sensorsus_02.jpa;

import javax.persistence.TypedQuery;
import static org.junit.Assert.*;
import org.junit.Test;
import static sensorsus_02.jpa.GenericTest.logger;

public class ProfissionalSaudeJpqlTest extends GenericTest{

    @Test
    public void profissionalSaudePorID() {
        logger.info("Executando profissionalSaudePorId()");
        TypedQuery<ProfissionalSaude> query = em.createQuery("SELECT ps FROM ProfissionalSaude ps WHERE ps.id = ?1",
                ProfissionalSaude.class);
        Long id = 5L;
        query.setParameter(1, id);
        ProfissionalSaude profissionalSaude = query.getSingleResult();
        assertEquals(id, profissionalSaude.getId());
    }

    @Test
    public void quantidadeTotalDeProfissionalSaudes() {
        logger.info("Executando quantidadeTotalDeProfissionalSaudes()");
        TypedQuery<Long> query = em.createNamedQuery("ProfissionalSaude.QuantidadeTotal",
                Long.class);
        Long total = query.getSingleResult();
        Long totalEsperado = 4L;
        assertEquals(totalEsperado, total);
    }
}
