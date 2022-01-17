package sensorsus_02.jpa;

import java.util.Calendar;
import java.util.List;
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
    
    @Test
    public void profissionalSaudePorPadraoConselhoRegional() {
        logger.info("Executando profissionalSaudePorPadraoConselhoRegional()");
        String jpql = "SELECT ps FROM ProfissionalSaude ps "
                + "WHERE ps.inscricaoConselhoRegional LIKE ?1";
        TypedQuery<ProfissionalSaude> query = em.createQuery(jpql, ProfissionalSaude.class);
        query.setParameter(1, "%111%");
        ProfissionalSaude profissionalSaude = query.getSingleResult();
        assertEquals("Amanda", profissionalSaude.getNome());
    }
    
    @Test
    public void profissionaisNascidosNosAnos80() {
        logger.info("Executando profissionaisPorDataNascimento()");
        TypedQuery<ProfissionalSaude> query = 
                em.createNamedQuery("ProfissionalSaude.NascidosNosAnos80", ProfissionalSaude.class);
        query.setParameter(1, getData(1, Calendar.JANUARY, 1980));
        query.setParameter(2, getData(31, Calendar.DECEMBER, 1989));
        List<ProfissionalSaude> profissionais = query.getResultList();
        String[] nomes = {"Silvio", "Jessica"};
        for (int i = 0; i < profissionais.size(); i++) {
            assertEquals(nomes[i], profissionais.get(i).getNome());
        }
    }
    
    @Test
    public void profissinaisPorEstabelecimento() {
        logger.info("Executando profissinaisPorEstabelecimento()");
        String jpql = "SELECT DISTINCT ps FROM ProfissionalSaude ps JOIN Avaliacao a "
                + "ON a MEMBER OF ps.avaliacoes WHERE a.estabelecimento = :estabelecimento";
        TypedQuery<ProfissionalSaude> query = em.createQuery(jpql, ProfissionalSaude.class);
        Estabelecimento estabelecimento = em.find(Estabelecimento.class, 5L);
        query.setParameter("estabelecimento", estabelecimento);
        ProfissionalSaude profissionalSaude = query.getSingleResult();
        Long id = 7L;
        assertEquals(id, profissionalSaude.getId());
        assertEquals("Camila", profissionalSaude.getNome());
    }
    
    @Test
    public void profissinaisPorBairro() {
        logger.info("Executando profissionaisPorBairro()");
        TypedQuery<ProfissionalSaude> query = em.createNamedQuery("ProfissionalSaude.PorBairro", 
                ProfissionalSaude.class);
        String bairro = "Derby";
        query.setParameter("bairro", bairro);
        ProfissionalSaude profissional = query.getSingleResult();
        assertEquals("Amanda", profissional.getNome());
    }
}
