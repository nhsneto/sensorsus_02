package sensorsus_02.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PROFISSIONAL_SAUDE")
@DiscriminatorValue(value = "PS")
@PrimaryKeyJoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
public class ProfissionalSaude extends Usuario implements Serializable {

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes;

    @Column(name = "TXT_NUMERO_CONSELHO_REGIONAL", nullable = false, length = 50)
    private String numeroConselhoRegional;

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
    
    public String getNumeroConselhoRegional() {
        return numeroConselhoRegional;
    }

    public void setNumeroConselhoRegional(String numeroConselhoRegional) {
        this.numeroConselhoRegional = numeroConselhoRegional;
    }
    
    @Override
    public String toString() {
        return "sensorsus_02.jpa.ProfissionalSaude[ id=" + id + " ]";
    }
}
