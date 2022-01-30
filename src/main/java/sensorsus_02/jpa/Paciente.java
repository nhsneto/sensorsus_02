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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_PACIENTE")
@NamedQueries(
    {
        @NamedQuery(
            name = "Paciente.pacientesComMenosDe60Anos",
            query = "SELECT p FROM Paciente p WHERE p.dataNascimento <= ?1"
        ),
        @NamedQuery(
            name = "Paciente.QuantidadeTotal",
            query = "SELECT COUNT(p) FROM Paciente p"
        ),
        @NamedQuery(
            name = "Paciente.PorAvaliacao",
            query = "SELECT pa FROM Paciente pa JOIN FETCH Avaliacao a "
                    + "WHERE :avaliacao MEMBER OF pa.avaliacoes"
        ),
        @NamedQuery(
            name = "Paciente.PorPadraoNumeroSus",
            query = "SELECT pa FROM Paciente pa WHERE pa.numeroSus LIKE :padrao ORDER BY pa.nome"
        ),
        @NamedQuery(
            name = "Paciente.PorNomeEstabelecimento",
            query = "SELECT DISTINCT pa FROM Paciente pa JOIN Avaliacao a ON a "
                    + "MEMBER OF pa.avaliacoes WHERE a.estabelecimento.nome = :nome "
                    + "ORDER BY pa.nome"
        )
    }
)
@DiscriminatorValue(value = "PA")
@PrimaryKeyJoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
public class Paciente extends Usuario implements Serializable {

    @OneToMany(mappedBy = "usuario", fetch = javax.persistence.FetchType.LAZY,
            cascade = javax.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes;

    @NotBlank(message = "{sensorsus_02.jpa.Paciente.numeroSus.blank}")
    @Size(min = 15, max = 15, message = "{sensorsus_02.jpa.Paciente.numeroSus.size}")
    @Pattern(regexp = "[0-9]{15}", message = "{sensorsus_02.jpa.Paciente.numeroSus}")
    @Column(name = "TXT_NUMERO_SUS")
    private String numeroSus;

    public String getNumeroSus() {
        return numeroSus;
    }

    public void setNumeroSus(String numeroSus) {
        this.numeroSus = numeroSus;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void adicionaAvaliacao(Avaliacao avaliacao) {
        if (this.avaliacoes == null) {
            this.avaliacoes = new ArrayList<>();
        }
        this.avaliacoes.add(avaliacao);
        avaliacao.setUsuario(this);
    }

    public boolean removeAvaliacao(Avaliacao avaliacao) {
        return avaliacoes.remove(avaliacao);
    }

    @Override
    public String toString() {
        return "sensorsus_02.jpa.Paciente[ id=" + id + " ]";
    }
}
