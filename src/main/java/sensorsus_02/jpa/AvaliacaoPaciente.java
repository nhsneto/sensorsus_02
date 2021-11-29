package sensorsus_02.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.eclipse.persistence.internal.jpa.parsing.GenerationContext;

@Entity
@Table(name = "TB_AVALIACAO_PACIENTE")
public class AvaliacaoPaciente implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO implementar a relacao com Paciente
    
    // TODO implementar a relacao com Estabelecimento
    
    // TODO implementar a relacao com Servico
    
    @Column(name = "TXT_COMENTARIO", nullable = false, length = 255)
    private String comentario;
}
