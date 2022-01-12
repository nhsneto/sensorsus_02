package sensorsus_02.jpa;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

public class AvaliacaoJpqlTest extends GenericTest {
    
    @Test
    public void avaliacaoPorID() {
        logger.info("Executando avaliacaoPorId()");
        TypedQuery<Avaliacao> query = em.createQuery("SELECT a FROM Avaliacao a WHERE a.id = ?1",
                Avaliacao.class);
        Long id = 2L;
        query.setParameter(1, id);
        Avaliacao avaliacao = query.getSingleResult();
        String comentario = "Fui vacinado muito rapidamente. Profissionais excelentes";
        assertEquals(id, avaliacao.getId());
        assertEquals(comentario, avaliacao.getComentario());
    }
    
    @Test
    public void avaliacoesSemUsuario() {
        logger.info("Executando avaliacoesSemUsuario()");
        TypedQuery<Avaliacao> query = em.createNamedQuery("Avaliacao.SemUsuario", Avaliacao.class);
        List<Avaliacao> avaliacoes = query.getResultList();
        assertEquals(0, avaliacoes.size());
    }
    
    @Test
    public void avaliacoesPorPadraoEmComentario() {
        logger.info("Executando avaliacoesPorPadraoEmComentario()");
        TypedQuery<Avaliacao> query = em.createQuery("SELECT a FROM Avaliacao a "
                + "WHERE LOWER(a.comentario) LIKE :padrao", Avaliacao.class);
        query.setParameter("padrao", "%fila%");
        List<Avaliacao> avaliacoes = query.getResultList();
        assertEquals(4, avaliacoes.size());
        avaliacoes.forEach(avaliacao -> {
            assertTrue(Pattern.compile("[F|f]ila").matcher(avaliacao.getComentario()).find());
        });
    }
    
    @Test
    public void avaliacoesComUsuariosJoinFetch() {
        logger.info("Executando avaliacoesComUsuariosJoinFetch()");
        TypedQuery<Avaliacao> query = em.createNamedQuery("Avaliacao.ComUsuariosJoinFetch", 
                Avaliacao.class);
        List<Avaliacao> avaliacoes = query.getResultList();
        assertEquals(11, avaliacoes.size());
        avaliacoes.forEach(avaliacao -> {
            assertNotNull(avaliacao.getUsuario());
        });
    }
    
    @Test
    public void avaliacoesPorBairro() {
        logger.info("Executando avaliacoesPorBairro()");
        TypedQuery<Avaliacao> query = em.createQuery("SELECT a FROM Avaliacao a "
                + "WHERE a.estabelecimento.endereco.bairro = :bairro", Avaliacao.class);
        query.setParameter("bairro", "Santo Amaro");
        List<Avaliacao> avaliacoes = query.getResultList();
        assertEquals(1, avaliacoes.size());
        String comentario = "O atendimento na recepção foi rápido, apesar da fila grande";
        assertEquals(comentario, avaliacoes.get(0).getComentario());
        String nome = "Jose";
        assertEquals(nome, avaliacoes.get(0).getUsuario().getNome());
    }
    
    @Test
    public void avaliacoesDeUsuariosAcimaDos60Anos() {
        logger.info("Executando avaliacoesDeUsuariosAcimaDos60Anos()");
        TypedQuery<Avaliacao> query = em.createNamedQuery("Avaliacao.UsuariosAcimaDe60Anos",
                Avaliacao.class);
        Calendar dataNascimento60Anos = Calendar.getInstance();
        dataNascimento60Anos.add(Calendar.YEAR, -60);
        query.setParameter(1, dataNascimento60Anos.getTime());
        List<Avaliacao> avaliacoes = query.getResultList();
        assertEquals(3, avaliacoes.size());
        avaliacoes.forEach(avaliacao -> {
            assertTrue(avaliacao.getUsuario().getDataNascimento()
                    .compareTo(dataNascimento60Anos.getTime()) <= 0);
        });
    }
    
    @Test
    public void avaliacoesPorTipoDeUsuario() {
        logger.info("Executando avaliacoesPorTipoDeUsuario()");
        TypedQuery<Avaliacao> query = em.createQuery("SELECT a FROM Avaliacao a "
                + "JOIN FETCH a.usuario u WHERE TYPE(u) = :tipoUsuario", 
                Avaliacao.class);
        query.setParameter("tipoUsuario", ProfissionalSaude.class);
        List<Avaliacao> avaliacoes = query.getResultList();
        assertEquals(5, avaliacoes.size());
        assertEquals("Amanda", nomeUsuario(avaliacoes.get(0)));
        assertEquals("Silvio", nomeUsuario(avaliacoes.get(1)));
        assertEquals("Silvio", nomeUsuario(avaliacoes.get(2)));
        assertEquals("Camila", nomeUsuario(avaliacoes.get(3)));
        assertEquals("Camila", nomeUsuario(avaliacoes.get(4)));
    }
    
    private String nomeUsuario(Avaliacao a) {
        return a.getUsuario().getNome();
    }
    
    @Test
    public void quantidadeTotalDeAvaliacoes() {
        logger.info("Executando quantidadeTotalDeAvaliacoes()");
        TypedQuery<Long> query = em.createNamedQuery("Avaliacao.QuantidadeTotal", 
                Long.class);
        Long total = query.getSingleResult();
        Long totalEsperado = 11L;
        assertEquals(totalEsperado, total);
    }
    
    @Test
    public void quantidadeAvaliacoesPorUsuario() {
        logger.info("Executando quantidadeAvaliacoesPorUsuario()");
        Query query = em.createQuery("SELECT COUNT(a), u.nome FROM Avaliacao a, Usuario u "
                + "WHERE a.usuario.nome = u.nome GROUP BY u ORDER BY u.nome");
        List<Object[]> resultados = query.getResultList();
        assertEquals("1 Amanda", avaliacoesUsuarioNome(resultados.get(0)));
        assertEquals("2 Camila", avaliacoesUsuarioNome(resultados.get(1)));
        assertEquals("1 Jose", avaliacoesUsuarioNome(resultados.get(2)));
        assertEquals("2 Raimunda", avaliacoesUsuarioNome(resultados.get(3)));
        assertEquals("2 Roberto", avaliacoesUsuarioNome(resultados.get(4)));
        assertEquals("1 Severina", avaliacoesUsuarioNome(resultados.get(5)));
        assertEquals("2 Silvio", avaliacoesUsuarioNome(resultados.get(6)));
    }
    
    private String avaliacoesUsuarioNome(Object[] resultado) {
        return resultado[0] + " " + resultado[1];
    }
    
    @Test
    public void avaliacoesPorSubstring() {
        logger.info("avaliacoesPorSubstring()");
        TypedQuery<Avaliacao> query = em.createQuery("SELECT a FROM Avaliacao a "
                + "WHERE SUBSTRING(LOWER(a.comentario), 1, 6) = 'poucos'", Avaliacao.class);
        List<Avaliacao> avaliacoes = query.getResultList();
        assertEquals(2, avaliacoes.size());
        assertEquals("Silvio", getUsuarioNome(avaliacoes.get(0)));
        assertEquals("Silvio", getUsuarioNome(avaliacoes.get(1)));
    }
    
    private String getUsuarioNome(Avaliacao a) {
        return a.getUsuario().getNome();
    }
}
