package sensorsus_02.jpa;

import java.util.List;
import javax.persistence.Query;
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
    
    @Test
    public void enderecoPorBairro() {
        logger.info("Executando enderecoPorBairro()");
        TypedQuery<Endereco> query = em.createNamedQuery("Endereco.PorBairro", Endereco.class);
        query.setParameter("bairro", "Cordeiro");
        Endereco endereco = query.getSingleResult();
        assertNotNull(endereco);
        Long id = 3L;
        assertEquals(id, endereco.getId());
        assertEquals("Av General San Martin", endereco.getLogradouro());
    }
    
    @Test
    public void enderecoPorPadraoLogradouro() {
        logger.info("Executando enderecoPorPadraoLegradouro()");
        TypedQuery<Endereco> query = em.createQuery("SELECT e FROM Endereco e "
                + "WHERE e.logradouro LIKE :padrao", Endereco.class);
        query.setParameter("padrao", "%Rua%");
        List<Endereco> enderecos = query.getResultList();
        assertEquals(2, enderecos.size());
        assertEquals("Rua Anorbio Marques", enderecos.get(0).getLogradouro());
        assertEquals("Rua Aprígio Guimarães", enderecos.get(1).getLogradouro());
    }
    
    @Test
    public void avaliacoesPorBairro() {
        logger.info("Executando avaliacoesPorBairro()");
        Query query = em.createNamedQuery("Endereco.avaliacoesPorBairro");
        List<Object[]> resultados = query.getResultList();
        assertEquals(5, resultados.size());
        assertEquals("Cordeiro 4", bairroAvaliacoes(resultados.get(0)));
        assertEquals("Derby 1", bairroAvaliacoes(resultados.get(1)));
        assertEquals("Iputinga 3", bairroAvaliacoes(resultados.get(2)));
        assertEquals("Santo Amaro 1", bairroAvaliacoes(resultados.get(3)));
        assertEquals("Tejipio 2", bairroAvaliacoes(resultados.get(4)));
    }
    
    private String bairroAvaliacoes(Object[] resultado) {
        return resultado[0] + " " + resultado[1];
    }
    
    @Test
    public void enderecoComMaiorNumeroAvaliacoes() {
        logger.info("Executando enderecoComMaiorNumeroAvaliacoes()");
        TypedQuery<Endereco> query = em.createQuery("SELECT DISTINCT e FROM Endereco e JOIN Estabelecimento es "
                + "WHERE e.numero = SELECT es.endereco.numero FROM Estabelecimento es "
                + "WHERE SIZE(es.avaliacoes) = SELECT MAX(SIZE(es.avaliacoes)) "
                + "FROM Estabelecimento es", Endereco.class);
        List<Endereco> enderecos = query.getResultList();
        assertEquals(1, enderecos.size());
        assertEquals("Cordeiro", enderecos.get(0).getBairro());
    }
    
    @Test
    public void enderecoSemEstabelecimento() {
        logger.info("Executando enderecoSemEstabelecimento()");
        TypedQuery<Endereco> query = em.createNamedQuery("Endereco.SemEstabelecimento", 
                Endereco.class);
        List<Endereco> enderecos = query.getResultList();
        assertEquals(0, enderecos.size());
    }
    
    @Test
    public void enderecoPorServico() {
        logger.info("Executando enderecoPorServico()");
        TypedQuery<Endereco> query = em.createQuery("SELECT e FROM Endereco e "
                + "JOIN Estabelecimento es ON e.id = es.id WHERE :servico MEMBER OF es.servicos", 
                Endereco.class);
        Servico servico = em.find(Servico.class, 4L);
        query.setParameter("servico", servico);
        List<Endereco> enderecos = query.getResultList();
        assertEquals(1, enderecos.size());
    }
    
    @Test
    public void enderecoPorAvaliacao() {
        logger.info("Executando enderecoPorAvaliacao()");
        TypedQuery<Endereco> query = em.createNamedQuery("Endereco.PorAvaliacao", Endereco.class);
        Avaliacao avaliacao = em.find(Avaliacao.class, 7L);
        query.setParameter("avaliacao", avaliacao);
        Endereco endereco = query.getSingleResult();
        Long id = 4L;
        assertEquals(id, endereco.getId());
        assertEquals("Tejipio", endereco.getBairro());
        assertEquals("Rua Aprígio Guimarães", endereco.getLogradouro());
    }
    
    @Test
    public void numerosELogradouroDosEnderecos() {
        logger.info("Executando numerosDosEnderecos");
        Query query = em.createQuery("SELECT e.numero, e.logradouro FROM Endereco e "
                + "ORDER BY e.numero");
        List<Object[]> resultados = query.getResultList();
        assertEquals(5, resultados.size());
        assertEquals("30 Av Caxangá", numeroLogradouro(resultados.get(0)));
        assertEquals("80 Rua Aprígio Guimarães", numeroLogradouro(resultados.get(1)));
        assertEquals("90 Av General San Martin", numeroLogradouro(resultados.get(2)));
        assertEquals("150 Av Agamenon Magalhães", numeroLogradouro(resultados.get(3)));
        assertEquals("375 Rua Anorbio Marques", numeroLogradouro(resultados.get(4)));
    }
    
    private String numeroLogradouro(Object[] coluna) {
        return coluna[0] + " " + coluna[1];
    }
    
    @Test
    public void enderecosNaoPertencentesPorCidade() {
        logger.info("Executando enderecosNaoPertencentesPorCidade()");
        TypedQuery<Endereco> query = em.createNamedQuery("Endereco.NaoPertencentePorCidade", 
                Endereco.class);
        query.setParameter("cidade", "recife");
        List<Endereco> enderecos = query.getResultList();
        assertEquals(0, enderecos.size());
    }
}
