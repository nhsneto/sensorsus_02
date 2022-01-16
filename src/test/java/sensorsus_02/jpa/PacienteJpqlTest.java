package sensorsus_02.jpa;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;


public class PacienteJpqlTest extends GenericTest {
    
    @Test
    public void pacientePorID() {
        logger.info("Executando pacientePorID()");
                TypedQuery<Paciente> query = em.createQuery("SELECT p FROM Paciente p WHERE p.id = ?1",
                Paciente.class);
        Long id = 2L;
        query.setParameter(1, id);
        Paciente paciente = query.getSingleResult();
        assertEquals(id, paciente.getId());
    }

    @Test
    public void pacientesComMenosDe60Anos() {
        logger.info("Executando pacientesComMenosDe60Anos()");
        TypedQuery<Paciente> query = em.createNamedQuery("Paciente.pacientesComMenosDe60Anos", Paciente.class);
        Calendar dataNascimento60Anos = Calendar.getInstance();
        dataNascimento60Anos.add(Calendar.YEAR, -60);
        query.setParameter(1, dataNascimento60Anos.getTime());
        List<Paciente> pacientes = query.getResultList();
        assertEquals(2, pacientes.size());
        pacientes.forEach(paciente -> {
            assertTrue(paciente.getDataNascimento()
                    .compareTo(dataNascimento60Anos.getTime()) <= 0);
        });
    }
    
    @Test
    public void quantidadeTotalDePacientes() {
        logger.info("Executando quantidadeTotalDePacientes()");
        TypedQuery<Long> query = em.createNamedQuery("Paciente.QuantidadeTotal", 
                Long.class);
        Long total = query.getSingleResult();
        Long totalEsperado = 4L;
        assertEquals(totalEsperado, total);
    }
    
    @Test
    public void pacientesNascidosEntreOsAnos40E60() {
        logger.info("Executando pacientesNascidosNosAnos80()");
        String jpql = "SELECT pa FROM Paciente pa WHERE pa.dataNascimento BETWEEN ?1 AND ?2 "
                + "ORDER BY pa.dataNascimento";
        TypedQuery<Paciente> query = em.createQuery(jpql, Paciente.class);
        query.setParameter(1, getData(1, Calendar.JANUARY, 1940));
        query.setParameter(2, getData(31, Calendar.DECEMBER, 1969));
        List<Paciente> pacientes = query.getResultList();
        assertEquals(3, pacientes.size());
        String[] nomes = {"Severina", "Roberto", "Raimunda"};
        for (int i = 0; i < pacientes.size(); i++) {
            assertEquals(nomes[i], pacientes.get(i).getNome());
        }
    }
    
    @Test
    public void pacientePorAvaliacao() {
        logger.info("Executando pacientePorAvaliacao()");
        TypedQuery<Paciente> query = em.createNamedQuery("Paciente.PorAvaliacao", Paciente.class);
        query.setParameter("avaliacao", em.find(Avaliacao.class, 2L));
        Paciente paciente = query.getSingleResult();
        assertEquals("Roberto", paciente.getNome());
    }
    
    @Test
    public void pacientePorLogin() {
        logger.info("Executando pacientePorLogin()");
        String jpql = "SELECT pa FROM Paciente pa WHERE pa.login = ?1";
        TypedQuery<Paciente> query = em.createQuery(jpql, Paciente.class);
        query.setParameter(1, "jfs6");
        Paciente paciente = query.getSingleResult();
        assertEquals("Jose", paciente.getNome());
    }
    
    @Test
    public void pacientesPorPadraoNumeroSus() {
        logger.info("Executando pacientesPorPadraoNumeroSus()");
        List<Paciente> pacientes = em.createNamedQuery("Paciente.PorPadraoNumeroSus", 
                Paciente.class).setParameter("padrao", "%333%").getResultList();
        assertEquals(2, pacientes.size());
        String[] nomes = {"Jose", "Severina"};
        for (int i = 0; i < pacientes.size(); i++) {
            assertEquals(nomes[i], pacientes.get(i).getNome());
        }
    }
    
    @Test
    public void pacientesPorPadraoEmail() {
        logger.info("Executando pacientesPorPadraoEmail()");
        TypedQuery<Paciente> query = em.createQuery("SELECT p FROM Paciente p WHERE p.email LIKE ?1", 
                Paciente.class);
        query.setParameter(1, "9@%");
        List<Paciente> pacientes = query.getResultList();
        String[] nomes = {"Jose", "Raimunda"};
        for (int i = 0; i < pacientes.size(); i++) {
            assertEquals(nomes[i], pacientes.get(i).getNome());
            Pattern.compile("[9@]*").matcher(pacientes.get(i).getEmail()).find();
        }
    }
}
