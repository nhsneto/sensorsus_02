package sensorsus_02.jpa;

import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.*;

public class ValidationEnderecoTest extends Teste {
    
    @Test(expected = ConstraintViolationException.class)
    public void persistirEnderecoInvalido() {
        Endereco endereco = null;
        try {
            endereco = new Endereco();
            endereco.setEstado("California"); // estado invalido
            endereco.setCidade(null); // cidade invalida
            endereco.setBairro(""); // bairro invalido
            endereco.setLogradouro("##############################################################"
                    + "###########################################################################"
                    + "###########################################################################"
                    + "################################################"); // logradouro invalido (> 255 caracteres)
            endereco.setNumero(-1);
            endereco.setComplemento("##############################################################"
                    + "###########################################################################"
                    + "###########################################################################"
                    + "################################################");
            em.persist(endereco);
            em.flush();
        } catch (ConstraintViolationException e) {
            assertEquals(6, e.getConstraintViolations().size());
            e.getConstraintViolations().forEach(violation -> {
                assertThat(violation.getPropertyPath() + " - " + violation.getMessage(), 
                    CoreMatchers.anyOf(
                        startsWith("estado - O campo estado é inválido"),
                        startsWith("cidade - O campo cidade não pode ser null ou vazio"),
                        startsWith("bairro - O campo bairro não pode ser null ou vazio"),
                        startsWith("logradouro - O campo logradouro deve possuir no máximo 255 caracteres"),
                        startsWith("numero - O campo número deve ser maior ou igual a 1"),
                        startsWith("complemento - O campo complemento deve possuir no máximo 255 caracteres")
                    ));
            });
            assertNull(endereco.getId());
            throw e;
        }
        
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void atualizarEnderecoInvalido() {
        Endereco endereco = em.find(Endereco.class, 1L);
        endereco.setEstado("Texas"); // valor de estado invalido
        endereco.setNumero(null); // valor de numero invalido
        try {
            em.flush();
        } catch (ConstraintViolationException e) {
            assertEquals(2, e.getConstraintViolations().size());
            e.getConstraintViolations().forEach(violation -> {
                assertThat(violation.getPropertyPath() + " - " + violation.getMessage(), 
                    CoreMatchers.anyOf(
                        startsWith("estado - O campo estado é inválido"),
                        startsWith("numero - O campo número não pode ser null")
                    ));
            });
            throw e;
        }
    }
}
