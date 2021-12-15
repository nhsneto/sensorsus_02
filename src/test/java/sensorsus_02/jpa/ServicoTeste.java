package sensorsus_02.jpa;

import static org.junit.Assert.*;
import org.junit.Test;

public class ServicoTeste extends Teste {
    
    @Test
    public void persistirServico() {
        Servico servico = new Servico();
        servico.setNome("Coleta de sangue");
        servico.setDepartamento("Enfermagem");
        
        em.persist(servico);
        em.flush();
        
        assertNotNull(servico.getId());
    }
}
