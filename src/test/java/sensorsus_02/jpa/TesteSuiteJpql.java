package sensorsus_02.jpa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    sensorsus_02.jpa.AvaliacaoJpqlTest.class,
    sensorsus_02.jpa.EnderecoJpqlTest.class,
    sensorsus_02.jpa.EstabelecimentoJpqlTest.class,
    sensorsus_02.jpa.PacienteJpqlTest.class,
    sensorsus_02.jpa.ProfissionalSaudeJpqlTest.class,
    sensorsus_02.jpa.ServicoJpqlTest.class,
})
public class TesteSuiteJpql {
    
}
