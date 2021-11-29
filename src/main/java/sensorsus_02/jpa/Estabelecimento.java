package sensorsus_02.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ESTABELECIMENTO")
public class Estabelecimento implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO implementar relacionamento com Endereco    

    // TODO implementar relacionamento com Servico
    
    // TODO implementar relacionamento com AvaliacaoPaciente
    
    // TODO implementar relacionamento com AvaliacaoProfissional
    
    @Column(name = "TXT_NOME", nullable = false, length = 255)
    private String nome;
    @Column(name = "TXT_CODIGO_CNES", nullable = false, unique = true, length = 40)
    private String codigoCnes;
}
