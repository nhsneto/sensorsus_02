package sensorsus_02.jpa;

import static org.junit.Assert.*;
import org.junit.Test;

public class EstabelecimentoTeste extends Teste {
    
    @Test
    public void persistirEstabelecimento() {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome("Upinha 24h Dr. Hélio Mendonça");
        estabelecimento.setCodigoCnes("999998888877777");
        
        Endereco endereco = new Endereco();
        endereco.setEstado("Pernambuco");
        endereco.setCidade("Recife");
        endereco.setBairro("Córrego do Jenipapo");
        endereco.setLogradouro("Av. da Recuperação");
        endereco.setNumero(225);
        
        estabelecimento.setEndereco(endereco);
        
        Servico servico = new Servico();
        servico.setNome("Vigilância dos Guardas Municipais");
        servico.setDepartamento("Segurança");
        
        estabelecimento.adicionaServico(servico);
        
        em.persist(estabelecimento);
        em.flush();
        
        assertNotNull(estabelecimento.getId());
        assertNotNull(servico.getId());
    }
    
}
