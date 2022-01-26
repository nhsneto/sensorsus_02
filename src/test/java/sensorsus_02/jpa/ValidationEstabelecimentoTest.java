package sensorsus_02.jpa;

import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.*;

public class ValidationEstabelecimentoTest extends Teste {
    
    @Test (expected = ConstraintViolationException.class)
    public void persistirEstabelecimentoInvalido() {
        Estabelecimento estabelecimento = null;
        try {
            estabelecimento = new Estabelecimento();
            estabelecimento.setNome(""); // nome invalido
            estabelecimento.setCodigoCnes("abcde12345"); // codigoCnes invalido
            estabelecimento.adicionaTelefone("1188888888"); // numero valido
            estabelecimento.adicionaTelefone("0011112222"); // numero invalido
            estabelecimento.adicionaTelefone("81abcdefgh"); // numero invalido
            estabelecimento.adicionaTelefone("1234"); // numero invalido, quarto telefone (O limite é de 3)
            em.persist(estabelecimento);
            em.flush();
        } catch (ConstraintViolationException e) {
            assertEquals(5, e.getConstraintViolations().size());
            e.getConstraintViolations().forEach(violation -> {
                assertThat(violation.getPropertyPath() + " - " + violation.getMessage(), 
                    CoreMatchers.anyOf(
                    startsWith("codigoCnes - O campo codigoCnes é inválido"),
                    startsWith("telefones - Um ou mais telefones informados são inválidos. "
                            + "O telefone deve possuir apenas caracteres numéricos e um ddd válido"),
                    startsWith("codigoCnes - O campo codigoCnes deve conter exatamente 15 caracteres numéricos"),
                    startsWith("telefones - O estabelecimento deve conter no máximo 3 telefones"),
                    startsWith("nome - O campo nome não pode ser null ou vazio")
                ));
            });
            assertNull(estabelecimento.getId());
            throw e;
        }
    }
    
    @Test (expected = ConstraintViolationException.class)
    public void atualizarEstabelecimentoInvalido() {
        String jpql = "SELECT e FROM Estabelecimento e WHERE e.nome = ?1";
        TypedQuery<Estabelecimento> query = em.createQuery(jpql, Estabelecimento.class);
        query.setParameter(1, "Hospital Getúlio Vargas");
        Estabelecimento estabelecimento = query.getSingleResult();
        estabelecimento.setCodigoCnes("codigoCnes1234"); // codigoCnes invalido
        estabelecimento.setNome(""); // nome invalido
        try {
            em.flush();
        } catch (ConstraintViolationException e) {
            assertEquals(3, e.getConstraintViolations().size());
            e.getConstraintViolations().forEach(violation -> {
                assertThat(violation.getPropertyPath() + " - " + violation.getMessage(), 
                    CoreMatchers.anyOf(
                    startsWith("codigoCnes - O campo codigoCnes é inválido"),
                    startsWith("codigoCnes - O campo codigoCnes deve conter exatamente 15 caracteres numéricos"),
                    startsWith("nome - O campo nome não pode ser null ou vazio")
                ));
            });
            throw e;
        }
    }
}
