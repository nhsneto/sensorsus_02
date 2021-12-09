package sensorsus_02.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "TB_PACIENTE")
@DiscriminatorValue(value = "PA")
@PrimaryKeyJoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
public class Paciente extends Usuario implements Serializable {

    @OneToMany(mappedBy = "paciente", fetch = javax.persistence.FetchType.LAZY,
            cascade = javax.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<AvaliacaoPaciente> avaliacoesPaciente;

    @Column(name = "TXT_NUMERO_SUS", nullable = false, length = 50)
    private String numeroSus;
    @Column(name = "TXT_LOGIN", nullable = false, length = 30)
    private String login;

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

    public List<AvaliacaoPaciente> getAvaliacoesPaciente() {
        return avaliacoesPaciente;
    }

    public void adicionar(AvaliacaoPaciente avaliacaoPaciente) {
        if (this.avaliacoesPaciente == null) {
            this.avaliacoesPaciente = new ArrayList<>();
        }

        this.avaliacoesPaciente.add(avaliacaoPaciente);
        avaliacaoPaciente.setPaciente(this);
    }

    public boolean remover(AvaliacaoPaciente avaliacaoPaciente) {
        return avaliacoesPaciente.remove(avaliacaoPaciente);
    }

    @Override
    public String toString() {
        return "sensorsus_02.jpa.Paciente[ id=" + id + " ]";
    }
}
