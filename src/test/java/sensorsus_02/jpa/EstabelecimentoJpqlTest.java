package sensorsus_02.jpa;

import java.util.List;
import javax.persistence.Query;
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
        assertEquals("Cordeiro", getBairro(estabelecimentos.get(0)));
        assertEquals("Derby", getBairro(estabelecimentos.get(1)));
        assertEquals("Iputinga", getBairro(estabelecimentos.get(2)));
        assertEquals("Santo Amaro", getBairro(estabelecimentos.get(3)));
        assertEquals("Tejipio", getBairro(estabelecimentos.get(4)));
    }
    
    @Test
    public void estabelecimentosPorBairroOrdemDecrescente() {
        logger.info("Executando estabelecimentosPorBairro()");
        TypedQuery<Estabelecimento> query = em.createQuery("SELECT e FROM Estabelecimento e "
                + "JOIN e.endereco en ORDER BY en.bairro DESC", Estabelecimento.class);
        List<Estabelecimento> estabelecimentos = query.getResultList();
        assertEquals(5, estabelecimentos.size());
        assertEquals("Tejipio", getBairro(estabelecimentos.get(0)));
        assertEquals("Santo Amaro", getBairro(estabelecimentos.get(1)));
        assertEquals("Iputinga", getBairro(estabelecimentos.get(2)));
        assertEquals("Derby", getBairro(estabelecimentos.get(3)));
        assertEquals("Cordeiro", getBairro(estabelecimentos.get(4)));
    }
    
    private String getBairro(Estabelecimento estabelecimento) {
        return estabelecimento.getEndereco().getBairro();
    }
    
    @Test
    public void estabelecimentosPorLogradouroSubstring() {
        logger.info("Executando estabelecimentosPorLogradouroSubstring()");
        TypedQuery<Estabelecimento> query = 
                em.createNamedQuery("Estabelecimento.PorLogradouroSubstring", 
                        Estabelecimento.class);
        List<Estabelecimento> estabelecimentos = query.getResultList();
        assertEquals(3, estabelecimentos.size());
        assertEquals("Hospital Barão de Lucena", estabelecimentos.get(0).getNome());
        assertEquals("Hospital Getúlio Vargas", estabelecimentos.get(1).getNome());
        assertEquals("Hospital Restauração", estabelecimentos.get(2).getNome());
    }
    
    @Test
    public void estabelecimentoPorPadraoDeCodigoCnes() {
        logger.info("Executando estabelecimentoPorPadraoDeCodigoCnes");
        TypedQuery<Estabelecimento> query = em.createQuery("SELECT e FROM Estabelecimento e "
                + "WHERE e.codigoCnes LIKE '%11111%' ORDER BY e.nome", Estabelecimento.class);
        List<Estabelecimento> estabelecimentos = query.getResultList();
        assertEquals(2, estabelecimentos.size());
        assertEquals("Hospital Oswaldo Cruz", estabelecimentos.get(0).getNome());
        assertEquals("Hospital Restauração", estabelecimentos.get(1).getNome());
    }
    
    @Test
    public void estabelecimentosQuantidadeAvaliacoes() {
        logger.info("Executando estabelecimentosQuantidadeAvaliacoes()");
        Query query = em.createQuery("SELECT e, COUNT(a) FROM Estabelecimento e, Avaliacao a "
                + "WHERE a MEMBER OF e.avaliacoes GROUP BY e ORDER BY e.nome");
        List<Object[]> resultados = query.getResultList();
        assertEquals(5, resultados.size());
        assertEquals("Hospital Barão de Lucena 3", estabelecimentoAvaliacoes(resultados.get(0)));
        assertEquals("Hospital Getúlio Vargas 4", estabelecimentoAvaliacoes(resultados.get(1)));
        assertEquals("Hospital Oswaldo Cruz 1", estabelecimentoAvaliacoes(resultados.get(2)));
        assertEquals("Hospital Otávio de Freitas 2", estabelecimentoAvaliacoes(resultados.get(3)));
        assertEquals("Hospital Restauração 1", estabelecimentoAvaliacoes(resultados.get(4)));
    }
    
    private String estabelecimentoAvaliacoes(Object[] objeto) {
        Estabelecimento estabelecimento = (Estabelecimento) objeto[0];
        Long avaliacoes = (Long) objeto[1];
        return estabelecimento.getNome() + " " + avaliacoes;
    }
    
    @Test
    public void estabelecimentoPorTelefone() {
        logger.info("Executando estabelecimentoPorTelefone()");
        TypedQuery<Estabelecimento> query = em.createNamedQuery("Estabelecimento.PorTelefone", 
                Estabelecimento.class);
        String telefone = "8177777777";
        query.setParameter("telefone", telefone);
        Estabelecimento estabelecimento = query.getSingleResult();
        assertEquals("Hospital Otávio de Freitas", estabelecimento.getNome());
        assertTrue(estabelecimento.getTelefones().contains(telefone));
    }
    
    @Test
    public void estabelecimentosComMaisDeUmTelefone() {
        logger.info("Executando estabelecimentosComMaisDeUmTelefone()");
        TypedQuery<Estabelecimento> query = em.createQuery("SELECT DISTINCT e FROM Estabelecimento e WHERE SIZE(e.telefones) > ?1", Estabelecimento.class);
        query.setParameter(1, 1);
        List<Estabelecimento> estabelecimentos = query.getResultList();
        estabelecimentos.forEach(estabelecimento -> {
            assertTrue(estabelecimento.getTelefones().size() > 1);
        });
    }
}
