package sensorsus_02.jpa;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import static org.junit.Assert.*;
import org.junit.Test;

public class AvaliacaoTeste extends Teste {
    
    @Test
    public void persistirAvaliacao() {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setComentario("Fila enorme para adquirir medicamentos. Apenas duas funcionárias "
                + "trabalhando no setor para atender muita gente o que faz o atendimento ser "
                + "bastante demorado! Passei quase 1 hora na fila! Insatisfação total!!!");
        
        Paciente paciente = new Paciente();
        paciente.setNome("Maria José");
        paciente.setNumeroSus("111000011110000");
        paciente.setEmail("mariajose1962@outlook.com");
        paciente.setLogin("mjfs44");
        paciente.setSenha("marijose1234");
        Calendar c = Calendar.getInstance();
        c.set(1962, Calendar.FEBRUARY, 16, 0, 0, 0);
        paciente.setDataNascimento(c.getTime());
        
        avaliacao.setUsuario(paciente);
        
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome("Farmácia do Estado");
        estabelecimento.setCodigoCnes("666665555544444");
        
        avaliacao.setEstabelecimento(estabelecimento);
        
        em.persist(avaliacao);
        em.flush();
        
        assertNotNull(avaliacao.getId());
        assertNotNull(paciente.getId());
        assertNotNull(estabelecimento.getId());
    }
    
    @Test
    public void consultarAvaliacao() {
        Avaliacao avaliacao = em.find(Avaliacao.class, 1L);
        assertNotNull(avaliacao);
        assertEquals("O atendimento na recepção foi rápido, apesar da fila grande", 
                avaliacao.getComentario());
        
        Usuario usuario = avaliacao.getUsuario();
        assertNotNull(usuario);
        assertEquals("Jose", usuario.getNome());
        assertEquals("josefreitas49@gmail.com", usuario.getEmail());
        assertEquals("jfs6", usuario.getLogin());
        assertEquals("jose1234", usuario.getSenha());
        Calendar c = Calendar.getInstance();
        c.set(1989, Calendar.MAY, 17, 0, 0, 0);
        assertEquals(c.getTime().toString(), usuario.getDataNascimento().toString());
        
        Estabelecimento estabelecimento = avaliacao.getEstabelecimento();
        assertNotNull(estabelecimento);
        assertEquals("Hospital Oswaldo Cruz", estabelecimento.getNome());
        assertEquals("111112222233333", estabelecimento.getCodigoCnes());
    }
    
    @Test
    public void atualizarAvaliacao() {
        String novoComentario = "Faz duas semanas que está faltando álcool em gel na entrada "
                + "principal do setor de traumatologia";
        Long id = 1L;
        Avaliacao avaliacao = em.find(Avaliacao.class, id);
        avaliacao.setComentario(novoComentario);
        em.flush();
        String jpql = "SELECT a FROM Avaliacao a WHERE a.id = ?1";
        TypedQuery<Avaliacao> query = em.createQuery(jpql, Avaliacao.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        avaliacao = query.getSingleResult();
        assertEquals(novoComentario, avaliacao.getComentario());
    }
    
    @Test
    public void atualizarAvaliacaoMerge() {
        String novoComentario = "Faz duas semanas que está faltando álcool em gel na entrada "
                + "principal do setor de traumatologia [merge]";
        Long id = 1L;
        Avaliacao avaliacao = em.find(Avaliacao.class, id);
        avaliacao.setComentario(novoComentario);
        em.clear();
        em.merge(avaliacao);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.retrieveMode", CacheRetrieveMode.BYPASS);
        avaliacao = em.find(Avaliacao.class, id, properties);
        assertEquals(novoComentario, avaliacao.getComentario());
    }
    
    @Test
    public void removerAvaliacao() {
        Avaliacao avaliacao = em.find(Avaliacao.class, 2L);
        em.remove(avaliacao);
        avaliacao = em.find(Avaliacao.class, 2L);
        assertNull(avaliacao);
        
        Usuario usuario = em.find(Usuario.class, 2L);
        assertNotNull(usuario);
        
        Estabelecimento estabelecimento = em.find(Estabelecimento.class, 2L);
        assertNotNull(estabelecimento);
    }
}
