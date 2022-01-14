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
}
