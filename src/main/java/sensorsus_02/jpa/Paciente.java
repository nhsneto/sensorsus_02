package sensorsus_02.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PACIENTE")
@DiscriminatorValue(value = "PA")
@PrimaryKeyJoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
public class Paciente extends Usuario implements Serializable {

    // TODO implementar relacionamento com AvaliacaoPaciente
    
    @Column(name = "TXT_NUMERO_SUS", nullable = false, length = 50)
    private String numeroSus;
    @Column(name = "TXT_LOGIN", nullable = false, length = 30)
    private String login;
    @Column(name = "TXT_SENHA", nullable = false, length = 15)
    private String senha;

    public String getNumeroSus() {
        return numeroSus;
    }

    public void setNumeroSus(String numeroSus) {
        this.numeroSus = numeroSus;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "sensorsus_02.jpa.Paciente[ id=" + id + " ]";
    }
}
