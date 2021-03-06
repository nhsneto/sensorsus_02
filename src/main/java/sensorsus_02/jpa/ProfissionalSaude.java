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
import javax.validation.constraints.NotBlank;

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
        ),
        @NamedQuery(
            name = "ProfissionalSaude.PorBairro",
            query = "SELECT DISTINCT ps FROM ProfissionalSaude ps JOIN Avaliacao a "
                    + "ON a MEMBER OF ps.avaliacoes "
                    + "WHERE a.estabelecimento.endereco.bairro = :bairro"
        ),
        @NamedQuery(
            name = "ProfissionalSaude.PorServico",
            query = "SELECT DISTINCT ps FROM ProfissionalSaude ps JOIN Avaliacao a "
                    + "ON a MEMBER OF ps.avaliacoes "
                    + "WHERE :servico MEMBER OF a.estabelecimento.servicos"
        ),
        @NamedQuery(
            name = "ProfissionalSaude.PorPadraoEmail",
            query = "SELECT ps FROM ProfissionalSaude ps WHERE ps.email LIKE :padrao"
        )
    }
)
@DiscriminatorValue(value = "PS")
@PrimaryKeyJoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
public class ProfissionalSaude extends Usuario implements Serializable {

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes;

    @NotBlank(message = "{sensorsus_02.jpa.ProfissionalSaude.inscricaoConselhoRegional.blank}")
    @ValidaConselhoRegional
    @Column(name = "TXT_INSCRICAO_CONSELHO_REGIONAL")
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
