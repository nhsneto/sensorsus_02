package sensorsus_02.jpa;

import java.util.List;
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
}
