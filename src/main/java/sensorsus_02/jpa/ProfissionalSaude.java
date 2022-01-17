package sensorsus_02.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PROFISSIONAL_SAUDE")
@NamedQueries(
    {
        @NamedQuery(
            name = "ProfissionalSaude.QuantidadeTotal",
            query = "SELECT COUNT(ps) FROM ProfissionalSaude ps"
        ),
        @NamedQuery(
            name = "ProfissionalSaude.NascidosNosAnos80",
            query = "SELECT ps FROM ProfissionalSaude ps WHERE ps.dataNascimento BETWEEN ?1 AND ?2"
        )
    }
)
@DiscriminatorValue(value = "PS")
@PrimaryKeyJoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
public class ProfissionalSaude extends Usuario implements Serializable {

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes;

    @Column(name = "TXT_INSCRICAO_CONSELHO_REGIONAL", nullable = false, length = 50)
    private String inscricaoConselhoRegional;

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
    
    public String getInscricaoConselhoRegional() {
        return inscricaoConselhoRegional;
    }

    public void setInscricaoConselhoRegional(String inscricaoConselhoRegional) {
        this.inscricaoConselhoRegional = inscricaoConselhoRegional;
    }
    
    @Override
    public String toString() {
        return "sensorsus_02.jpa.ProfissionalSaude[ id=" + id + " ]";
    }
}
