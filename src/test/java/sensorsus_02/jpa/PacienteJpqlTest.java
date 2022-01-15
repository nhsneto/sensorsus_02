package sensorsus_02.jpa;

import java.util.Calendar;
import java.util.List;
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
}
