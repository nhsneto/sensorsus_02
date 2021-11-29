package sensorsus_02.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SERVICO")
public class Servico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO implementar o relacionamento com AvaliacaoPaciente
    
    // TODO implementar o relacionamento com Estabelecimento (apenas na parte do estabelecimento)
    // slide aula 03
    
    @Column(name = "TXT_NOME", nullable = false, length = 200)
    private String nome;
    @Column(name = "TXT_DEPARTAMENTO", nullable = true, length = 200)
    private String departamento;
}
