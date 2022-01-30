package sensorsus_02.jpa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    sensorsus_02.jpa.ValidationAvaliacaoTest.class,
    sensorsus_02.jpa.ValidationEnderecoTest.class,
    sensorsus_02.jpa.ValidationEstabelecimentoTest.class,
    sensorsus_02.jpa.ValidationPacienteTest.class,
    sensorsus_02.jpa.ValidationProfissionalSaudeTest.class,
    sensorsus_02.jpa.ValidationServicoTest.class
})
public class TesteSuiteValidation {
    
}
