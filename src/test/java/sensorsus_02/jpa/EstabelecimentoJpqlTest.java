package sensorsus_02.jpa;

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
}
