package sensorsus_02.jpa;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import static org.junit.Assert.*;
import org.junit.Test;

public class EstabelecimentoTeste extends Teste {
    
    @Test
    public void persistirEstabelecimento() {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome("Upinha 24h Dr. Hélio Mendonça");
        estabelecimento.setCodigoCnes("999998888877777");
        
        Endereco endereco = new Endereco();
        endereco.setEstado("Pernambuco");
        endereco.setCidade("Recife");
        endereco.setBairro("Córrego do Jenipapo");
        endereco.setLogradouro("Av. da Recuperação");
        endereco.setNumero(225);
        
        estabelecimento.setEndereco(endereco);
        
        Servico servico = new Servico();
        servico.setNome("Vigilância dos Guardas Municipais");
        servico.setDepartamento("Segurança");
        
        estabelecimento.adicionaServico(servico);
        
        em.persist(estabelecimento);
        em.flush();
        
        assertNotNull(estabelecimento.getId());
        assertNotNull(endereco.getId());
        assertNotNull(servico.getId());
    }
    
    @Test
    public void consultarEstabelecimento() {
        Estabelecimento estabelecimento = em.find(Estabelecimento.class, 2L);
        assertNotNull(estabelecimento);
        assertEquals("Hospital Restauração", estabelecimento.getNome());
        assertEquals("333332222211111", estabelecimento.getCodigoCnes());
        
        Endereco endereco = estabelecimento.getEndereco();
        assertNotNull(endereco);
        assertEquals("Pernambuco", endereco.getEstado());
        assertEquals("Recife", endereco.getCidade());
        assertEquals("Derby", endereco.getBairro());
        assertEquals("Av Agamenon Magalhães", endereco.getLogradouro());
        Integer numero = 150;
        assertEquals(numero, endereco.getNumero());
        
        Servico servico1 = estabelecimento.getServicos().get(0);
        assertEquals("Recepção do Paciente", servico1.getNome());
        assertEquals("Administração", servico1.getDepartamento());
        
        Servico servico2 = estabelecimento.getServicos().get(1);
        assertEquals("Limpeza da Recepção", servico2.getNome());
        assertEquals("Serviços Gerais", servico2.getDepartamento());
        
        assertTrue(estabelecimento.getTelefones().contains("8133333333"));
        assertTrue(estabelecimento.getTelefones().contains("8144444444"));
    }
    
    @Test
    public void atualizarEstabelecimento() {
        String novoNome = "Hospital da Restauração Governador Paulo Guerra";
        Long id = 2L;
        Estabelecimento estabelecimento = em.find(Estabelecimento.class, id);
        estabelecimento.setNome(novoNome);
        em.flush();
        String jpql = "SELECT e FROM Estabelecimento e WHERE e.id = ?1";
        TypedQuery<Estabelecimento> query = em.createQuery(jpql, Estabelecimento.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        estabelecimento = query.getSingleResult();
        assertEquals(novoNome, estabelecimento.getNome());
    }
    
    @Test
    public void atualizarEstabelecimentoMerge() {
        String novoNome = "Hospital da Restauração Governador Paulo Guerra [merge]";
        Long id = 2L;
        Estabelecimento estabelecimento = em.find(Estabelecimento.class, id);
        estabelecimento.setNome(novoNome);
        em.clear();
        em.merge(estabelecimento);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        estabelecimento = em.find(Estabelecimento.class, id, properties);
        assertEquals(novoNome, estabelecimento.getNome());
    }
    
    @Test
    public void removerEstabelecimento() {
        Estabelecimento estabelecimento = em.find(Estabelecimento.class, 1L);
        em.remove(estabelecimento);
        estabelecimento = em.find(Estabelecimento.class, 1L);
        assertNull(estabelecimento);
        
        Endereco endereco = em.find(Endereco.class, 1L);
        assertNull(endereco);
        
        Servico servico1 = em.find(Servico.class, 1L);
        assertNotNull(servico1);
        Servico servico2 = em.find(Servico.class, 2L);
        assertNotNull(servico2);
        
        Avaliacao avaliacao = em.find(Avaliacao.class, 1L);
        assertNull(avaliacao);
        
        Usuario usuario1 = em.find(Usuario.class, 1L);
        assertNotNull(usuario1);
        Usuario usuario2 = em.find(Usuario.class, 2L);
        assertNotNull(usuario2);
    }
}
