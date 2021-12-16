package sensorsus_02.jpa;

import static org.junit.Assert.*;
import org.junit.Test;

public class EnderecoTeste extends Teste {
    
    @Test
    public void persistirEndereco() {
        Endereco endereco = new Endereco();
        endereco.setEstado("Pernambuco");
        endereco.setCidade("Recife");
        endereco.setBairro("Soledade");
        endereco.setLogradouro("Rua Osvaldo Cruz");
        endereco.setNumero(200);
        
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome("Farmácia do Estado de Pernambuco");
        estabelecimento.setCodigoCnes("00000555550000055555");
        estabelecimento.setEndereco(endereco);
        
        em.persist(endereco);
        em.flush();
        
        assertNotNull(endereco.getId());
        assertNotNull(estabelecimento.getId());
    }
}
