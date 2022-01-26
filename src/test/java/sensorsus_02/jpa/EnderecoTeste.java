package sensorsus_02.jpa;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import static org.junit.Assert.*;
import org.junit.Test;

public class EnderecoTeste extends Teste {
    
    @Test
    public void persistirEndereco() {
        Endereco endereco = new Endereco();
        endereco.setEstado("Pernambuco");
        endereco.setCidade("Recife");
        endereco.setBairro("Soledade");
        endereco.setLogradouro("Rua Osvaldo Cruz");
        endereco.setNumero(200);
        
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome("Farmácia do Estado de Pernambuco");
        estabelecimento.setCodigoCnes("999998888877777");
        estabelecimento.setEndereco(endereco);
        
        em.persist(endereco);
        em.flush();
        
        assertNotNull(endereco.getId());
        assertNotNull(estabelecimento.getId());
    }
    
    @Test
    public void consultarEndereco() {
        Endereco endereco = em.find(Endereco.class, 2L);
        assertNotNull(endereco);
        assertEquals("Pernambuco", endereco.getEstado());
        assertEquals("Recife", endereco.getCidade());
        assertEquals("Derby", endereco.getBairro());
        assertEquals("Av Agamenon Magalhães", endereco.getLogradouro());
        Integer numero = 150;
        assertEquals(numero, endereco.getNumero());
        
        Estabelecimento estabelecimento = endereco.getEstabelecimento();
        assertNotNull(estabelecimento);
        assertEquals("Hospital Restauração", estabelecimento.getNome());
        assertEquals("333332222211111", estabelecimento.getCodigoCnes());
    }
    
    @Test
    public void atualizarEndereco() {
        Integer novoNumero = 377;
        String novoLogradouro = "Rua Arnóbio Marquês";
        Long id = 1L;
        Endereco endereco = em.find(Endereco.class, id);
        endereco.setNumero(novoNumero);
        endereco.setLogradouro(novoLogradouro);
        em.flush();
        String jpql = "SELECT e FROM Endereco e WHERE e.id = ?1";
        TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        endereco = query.getSingleResult();
        assertEquals(novoNumero, endereco.getNumero());
        assertEquals(novoLogradouro, endereco.getLogradouro());
    }
    
    @Test
    public void atualizarEnderecoMerge() {
        Integer novoNumero = 378;
        String novoLogradouro = "Rua Arnóbio Marquês [merge]";
        Long id = 1L;
        Endereco endereco = em.find(Endereco.class, id);
        endereco.setNumero(novoNumero);
        endereco.setLogradouro(novoLogradouro);
        em.clear();
        em.merge(endereco);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        endereco = em.find(Endereco.class, id, properties);
        assertEquals(novoNumero, endereco.getNumero());
        assertEquals(novoLogradouro, endereco.getLogradouro());
    }
    
    @Test
    public void removerEndereco() {
        Endereco endereco = em.find(Endereco.class, 1L);
        em.remove(endereco);
        endereco = em.find(Endereco.class, 1L);
        assertNull(endereco);
        
        Estabelecimento estabelecimento = em.find(Estabelecimento.class, 1L);
        assertNull(estabelecimento);
    }
}
