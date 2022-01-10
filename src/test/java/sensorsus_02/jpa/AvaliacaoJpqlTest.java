package sensorsus_02.jpa;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;
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
}
