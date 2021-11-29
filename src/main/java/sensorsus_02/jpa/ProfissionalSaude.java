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

    @OneToMany(mappedBy = "profissionalSaude", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvaliacaoProfissional> avaliacoesProfissional;

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

    public List<AvaliacaoProfissional> getAvaliacoesProfissional() {
        return avaliacoesProfissional;
    }
    
    public void adicionar(AvaliacaoProfissional avaliacaoProfissional) {
        if (this.avaliacoesProfissional == null) {
            this.avaliacoesProfissional = new ArrayList<>();
        }
        this.avaliacoesProfissional.add(avaliacaoProfissional);
        avaliacaoProfissional.setProfissionalSaude(this);
    }
    
    public void remover(AvaliacaoProfissional avaliacaoProfissional) {
        this.avaliacoesProfissional.remove(avaliacaoProfissional);
    }
}
