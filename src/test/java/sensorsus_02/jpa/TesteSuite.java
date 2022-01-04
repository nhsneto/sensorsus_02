package sensorsus_02.jpa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({sensorsus_02.jpa.AvaliacaoTeste.class, sensorsus_02.jpa.EnderecoTeste.class,
    sensorsus_02.jpa.EstabelecimentoTeste.class, sensorsus_02.jpa.PacienteTeste.class,
    sensorsus_02.jpa.ProfissionalSaudeTeste.class, sensorsus_02.jpa.ServicoTeste.class})
public class TesteSuite {
    
}
