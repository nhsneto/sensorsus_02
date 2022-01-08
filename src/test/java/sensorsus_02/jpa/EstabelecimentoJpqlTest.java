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
    
    @Test
    public void estabelecimentosSemServicos() {
        logger.info("Executando estabelecimentosSemServicos()");
        TypedQuery<Estabelecimento> query = 
                em.createQuery("SELECT e FROM Estabelecimento e WHERE e.servicos IS EMPTY", 
                        Estabelecimento.class);
        List<Estabelecimento> estabelecimentos = query.getResultList();
        assertTrue(estabelecimentos.isEmpty());
    }
    
    @Test
    public void estabelecimentosPorCidade() {
        logger.info("Executando estabelecimentosPorCidade()");
        TypedQuery<Estabelecimento> query = em.createNamedQuery("Estabelecimento.PorCidade", 
                Estabelecimento.class);
        String cidade = "Recife";
        query.setParameter("cidade", cidade);
        List<Estabelecimento> estabelecimentos = query.getResultList();
        assertEquals(5, estabelecimentos.size());
    }
    
    @Test
    public void estabelecimentoComOMaiorNumeroDeAvaliacoes() {
        logger.info("Executando estabelecimentoComOMaiorNumeroDeAvaliacoes()");
        TypedQuery<Estabelecimento> query = 
                em.createNamedQuery("Estabelecimento.MaiorNumeroDeAvaliacoes", Estabelecimento.class);
        Estabelecimento estabelecimento = query.getSingleResult();
        assertEquals(4, estabelecimento.getAvaliacoes().size());
    }
    
    @Test
    public void estabelecimentosPorBairroOrdemCrescente() {
        logger.info("Executando estabelecimentosPorBairro()");
        TypedQuery<Estabelecimento> query = em.createQuery("SELECT e FROM Estabelecimento e "
                + "JOIN e.endereco en ORDER BY en.bairro", Estabelecimento.class);
        List<Estabelecimento> estabelecimentos = query.getResultList();
        assertEquals(5, estabelecimentos.size());
        assertEquals("Cordeiro", estabelecimentos.get(0).getEndereco().getBairro());
        assertEquals("Derby", estabelecimentos.get(1).getEndereco().getBairro());
        assertEquals("Iputinga", estabelecimentos.get(2).getEndereco().getBairro());
        assertEquals("Santo Amaro", estabelecimentos.get(3).getEndereco().getBairro());
        assertEquals("Tejipio", estabelecimentos.get(4).getEndereco().getBairro());
    }
    
    @Test
    public void estabelecimentosPorBairroOrdemDecrescente() {
        logger.info("Executando estabelecimentosPorBairro()");
        TypedQuery<Estabelecimento> query = em.createQuery("SELECT e FROM Estabelecimento e "
                + "JOIN e.endereco en ORDER BY en.bairro DESC", Estabelecimento.class);
        List<Estabelecimento> estabelecimentos = query.getResultList();
        assertEquals(5, estabelecimentos.size());
        assertEquals("Tejipio", estabelecimentos.get(0).getEndereco().getBairro());
        assertEquals("Santo Amaro", estabelecimentos.get(1).getEndereco().getBairro());
        assertEquals("Iputinga", estabelecimentos.get(2).getEndereco().getBairro());
        assertEquals("Derby", estabelecimentos.get(3).getEndereco().getBairro());
        assertEquals("Cordeiro", estabelecimentos.get(4).getEndereco().getBairro());
    }
}
