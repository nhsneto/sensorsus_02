package sensorsus_02.jpa;

import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidationServicoTest extends Teste {
    
    @Test(expected = ConstraintViolationException.class)
    public void persistirServicoInvalido() {
        Servico servico = null;
        try {
            servico = new Servico();
            servico.setNome("");
            servico.setDepartamento("##############################################################"
                    + "###########################################################################"
                    + "###########################################################################"
                    + "######################################################");
            em.persist(servico);
            em.flush();
        } catch (ConstraintViolationException e) {
            assertEquals(2, e.getConstraintViolations().size());
            e.getConstraintViolations().forEach(violation -> {
                assertThat(violation.getPropertyPath() + " - " + violation.getMessage(), 
                    CoreMatchers.anyOf(
                        startsWith("nome - O campo nome não pode ser null ou vazio"),
                        startsWith("departamento - O campo departamento deve possuir no máximo 255 caracteres")
                    ));
            });
            assertNull(servico.getId());
            throw e;
        }
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void atualizarServicoInvalido() {
        Servico servico = em.find(Servico.class, 4L);
        servico.setNome("##############################################################"
                    + "###########################################################################"
                    + "###########################################################################"
                    + "######################################################");
        servico.setDepartamento(null);
        try {
            em.flush();
        } catch (ConstraintViolationException e) {
            assertEquals(2, e.getConstraintViolations().size());
            e.getConstraintViolations().forEach(violation -> {
                assertThat(violation.getPropertyPath() + " - " + violation.getMessage(), 
                    CoreMatchers.anyOf(
                        startsWith("departamento - O campo departamento não pode ser null ou vazio"),
                        startsWith("nome - O campo nome deve possuir no máximo 255 caracteres")
                    ));
            });
            throw e;
        }
    }
}
