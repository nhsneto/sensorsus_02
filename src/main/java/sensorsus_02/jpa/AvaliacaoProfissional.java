package sensorsus_02.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_AVALIACAO_PROFISSIONAL")
public class AvaliacaoProfissional implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO implementar a relacao com ProfissionalSaude
    
    // TODO implementar a relacao com Estabelecimento
    
    @Column(name = "TXT_COMENTARIO", nullable = false, length = 255)
    private String comentario;
}
