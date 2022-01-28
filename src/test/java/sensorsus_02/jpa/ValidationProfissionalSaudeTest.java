package sensorsus_02.jpa;

import java.util.Calendar;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidationProfissionalSaudeTest extends Teste {
    
    @Test(expected = ConstraintViolationException.class)
    public void persistirProfissionalSaudeInvalido() {
        ProfissionalSaude profissionalSaude = null;
        try {
            profissionalSaude = new ProfissionalSaude();
            profissionalSaude.setNome(""); // nome invalido
            Calendar dataNascimento = Calendar.getInstance();
            dataNascimento.set(2050, Calendar.OCTOBER, 29, 0, 0, 0);
            profissionalSaude.setDataNascimento(dataNascimento.getTime()); // dataNascimento invalida
            profissionalSaude.setEmail("1234abcd"); // email invalido
            profissionalSaude.setInscricaoConselhoRegional("crm"); // inscricaoConselhoRegional invalido
            profissionalSaude.setLogin("1234helena"); // login invalido
            profissionalSaude.setSenha("helenacartaxo1234567890"); // senha invalida
            em.persist(profissionalSaude);
            em.flush();
        } catch (ConstraintViolationException e) {
            assertEquals(7, e.getConstraintViolations().size());
            e.getConstraintViolations().forEach(violation -> {
                assertThat(violation.getPropertyPath() + " - " + violation.getMessage(), 
                    CoreMatchers.anyOf(
                    startsWith("nome - O campo nome não pode ser null ou vazio"),
                    startsWith("dataNascimento - O campo dataNascimento deve possuir uma data do passado"),
                    startsWith("email - O campo email é inválido"),
                    startsWith("inscricaoConselhoRegional - O campo inscricaoConselhoRegional é inválido"),
                    startsWith("login - O campo login é inválido"),
                    startsWith("senha - O campo senha deve possuir no máximo 15 caracteres"),
                    startsWith("senha - O campo senha é inválido")
                ));
            });
            assertNull(profissionalSaude.getId());
            throw e;
        }
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void atualizarProfissionalSaudeInvalido() {
        String jpql = "SELECT p FROM ProfissionalSaude p WHERE p.dataNascimento = ?1";
        TypedQuery<ProfissionalSaude> query = em.createQuery(jpql, ProfissionalSaude.class);
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.set(1982, Calendar.APRIL, 02, 0, 0, 0);
        query.setParameter(1, dataNascimento.getTime());
        ProfissionalSaude profissional = query.getSingleResult();
        dataNascimento.set(2035, Calendar.APRIL, 02, 0, 0, 0);
        profissional.setDataNascimento(dataNascimento.getTime());
        profissional.setInscricaoConselhoRegional("crm-tx12345");
        try {
            em.flush();
        } catch (ConstraintViolationException e) {
            assertEquals(2, e.getConstraintViolations().size());
            e.getConstraintViolations().forEach(violation -> {
                assertThat(violation.getPropertyPath() + " - " + violation.getMessage(), 
                    CoreMatchers.anyOf(
                    startsWith("dataNascimento - O campo dataNascimento deve possuir uma data do passado"),
                    startsWith("inscricaoConselhoRegional - O campo inscricaoConselhoRegional é inválido")
                ));
            });
            throw e;
        }
    }
}
