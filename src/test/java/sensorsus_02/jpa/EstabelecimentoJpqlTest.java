package sensorsus_02.jpa;

import java.util.List;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

public class EstabelecimentoJpqlTest extends GenericTest {
    
    @Test
    public void logradouroDoEstabelecimentoPorNome() {
        logger.info("Executando logradouroDoEstabelecimentoPorNome()");
        TypedQuery<Estabelecimento> query = 
                em.createQuery("SELECT e FROM Estabelecimento e WHERE e.nome = :nome", 
                        Estabelecimento.class);
        query.setParameter("nome", "Hospital Oswaldo Cruz");
        Estabelecimento estabelecimento = query.getSingleResult();
        String logradouro = "Rua Anorbio Marques";
        assertEquals(logradouro, estabelecimento.getEndereco().getLogradouro());
    }
    
    @Test
    public void estabelecimentosSemAvaliacoesNamedQuery() {
        logger.info("Executando estabelecimentosSemAvaliacoesNamedQuery()");
        TypedQuery<Estabelecimento> query = em.createNamedQuery("Estabelecimento.SemAvaliacoes", 
                Estabelecimento.class);
        List<Estabelecimento> estabelecimentos = query.getResultList();
        assertTrue(estabelecimentos.isEmpty());
    }
    
    @Test
    public void estabelecimentosComExatamente2Servicos() {
        logger.info("Executando estabelecimentosComExatamente2Servicos");
        TypedQuery<Estabelecimento> query = 
                em.createQuery("SELECT e FROM Estabelecimento e WHERE SIZE(e.servicos) = ?1", 
                        Estabelecimento.class);
        query.setParameter(1, 2);
        List<Estabelecimento> estabelecimentos = query.getResultList();
        estabelecimentos.forEach(estabelecimento -> {
            assertEquals(2, estabelecimento.getServicos().size());
        });
    }
}
