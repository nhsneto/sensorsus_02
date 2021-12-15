package sensorsus_02.jpa;

import java.util.Calendar;
import static org.junit.Assert.*;
import org.junit.Test;

public class AvaliacaoTeste extends Teste {
    
    @Test
    public void persistirAvaliacao() {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setComentario("Fila enorme para adquirir medicamentos. Apenas duas funcionárias "
                + "trabalhando no setor para atender muita gente o que faz o atendimento ser "
                + "bastante demorado! Passei quase 1 hora na fila! Insatisfação total!!!");
        
        Paciente paciente = new Paciente();
        paciente.setNome("Maria José");
        paciente.setNumeroSus("111000011110000");
        paciente.setEmail("mariajose1962@outlook.com");
        paciente.setLogin("mjfs44");
        paciente.setSenha("marijose1234");
        Calendar c = Calendar.getInstance();
        c.set(1962, Calendar.FEBRUARY, 16, 0, 0, 0);
        paciente.setDataNascimento(c.getTime());
        
        avaliacao.setUsuario(paciente);
        
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome("Farmácia do Estado");
        estabelecimento.setCodigoCnes("666665555544444");
        
        avaliacao.setEstabelecimento(estabelecimento);
        
        em.persist(avaliacao);
        em.flush();
        
        assertNotNull(avaliacao.getId());
        assertNotNull(paciente.getId());
        assertNotNull(estabelecimento.getId());
    }
    
    @Test
    public void consultarAvaliacao() {
        Avaliacao avaliacao = em.find(Avaliacao.class, 1L);
        assertNotNull(avaliacao);
        assertEquals("O atendimento na recepção foi rápido, apesar da fila grande", 
                avaliacao.getComentario());
        
        Usuario usuario = avaliacao.getUsuario();
        assertNotNull(usuario);
        assertEquals("Jose", usuario.getNome());
        assertEquals("josefreitas49@gmail.com", usuario.getEmail());
        assertEquals("jfs6", usuario.getLogin());
        assertEquals("jose1234", usuario.getSenha());
        Calendar c = Calendar.getInstance();
        c.set(1989, Calendar.MAY, 17, 0, 0, 0);
        assertEquals(c.getTime().toString(), usuario.getDataNascimento().toString());
        
        Estabelecimento estabelecimento = avaliacao.getEstabelecimento();
        assertNotNull(estabelecimento);
        assertEquals("Hospital Oswaldo Cruz", estabelecimento.getNome());
        assertEquals("111112222233333", estabelecimento.getCodigoCnes());
    }
}
