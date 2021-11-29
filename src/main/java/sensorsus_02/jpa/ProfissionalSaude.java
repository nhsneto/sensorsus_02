package sensorsus_02.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PROFISSIONAL_SAUDE")
@DiscriminatorValue(value = "PS")
@PrimaryKeyJoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
public class ProfissionalSaude extends Usuario implements Serializable {
    
    // TODO implementar relacionamento com AvaliacaoProfissional
    
    @Column(name = "TXT_CODIGO_CNS", nullable = false, length = 50)
    private String codigoCns;
    @Column(name = "TXT_MATRICULA", nullable = false, length = 30)
    private String matricula;
    @Column(name = "TXT_SENHA", nullable = false, length = 15)
    private String senha;

    public String getCodigoCns() {
        return codigoCns;
    }

    public void setCodigoCns(String codigoCns) {
        this.codigoCns = codigoCns;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
