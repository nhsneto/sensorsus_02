package sensorsus_02.jpa;

import java.util.Calendar;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class ValidationPacienteTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirPacienteInvalido() {
        Paciente Paciente = null;
        try {
            Paciente = new Paciente();
            Paciente.setNome(""); // nome invalido
            Calendar dataNascimento = Calendar.getInstance();
            dataNascimento.set(2150, Calendar.OCTOBER, 29, 0, 0, 0);
            Paciente.setDataNascimento(dataNascimento.getTime()); // dataNascimento invalida
            Paciente.setEmail("testeTeste.com"); // email invalido
            Paciente.setNumeroSus("123456789"); // número cartão sus invalido
            Paciente.setLogin("!@123jose"); // login invalido
            Paciente.setSenha("@josedasilvasantos1234567890"); // senha invalida
            em.persist(Paciente);
            em.flush();
        } catch (ConstraintViolationException e) {
            assertEquals(7, e.getConstraintViolations().size());
            e.getConstraintViolations().forEach(violation -> {
                assertThat(violation.getPropertyPath() + " - " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("nome - O campo nome não pode ser null ou vazio"),
                                startsWith("dataNascimento - O campo dataNascimento deve possuir uma data do passado"),
                                startsWith("email - O campo email é inválido"),
                                startsWith("numeroSus - O campo numeroSUS deve ter 15 caracteres"),
                                startsWith("numeroSus - O campo numeroSus é inválido"),
                                startsWith("login - O campo login é inválido"),
                                startsWith("senha - O campo senha deve possuir no máximo 15 caracteres")
                        ));
            });
            assertNull(Paciente.getId());
            throw e;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarPacienteInvalido() {
        String jpql = "SELECT p FROM Paciente p WHERE p.dataNascimento = ?1";
        TypedQuery<Paciente> query = em.createQuery(jpql, Paciente.class);
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.set(1989, Calendar.MAY, 17, 0, 0, 0);
        query.setParameter(1, dataNascimento.getTime());
        Paciente paciente = query.getSingleResult();
        dataNascimento.set(2350, Calendar.APRIL, 02, 0, 0, 0);
        paciente.setDataNascimento(dataNascimento.getTime());
        paciente.setNumeroSus("@#!1321321321321321321321");
        try {
            em.flush();
        } catch (ConstraintViolationException e) {
            assertEquals(3, e.getConstraintViolations().size());
            e.getConstraintViolations().forEach(violation -> {
                assertThat(violation.getPropertyPath() + " - " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("dataNascimento - O campo dataNascimento deve possuir uma data do passado"),
                                startsWith("numeroSus - O campo numeroSUS deve ter 15 caracteres"),
                                startsWith("numeroSus - O campo numeroSus é inválido")
                        ));
            });
            throw e;
        }
    }
}
