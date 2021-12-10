package sensorsus_02.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SERVICO")
public class Servico implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy = "servico", fetch = FetchType.LAZY, cascade = CascadeType.ALL, 
            orphanRemoval = true)
    private List<AvaliacaoPaciente> avaliacoesPaciente;
    
    @Column(name = "TXT_NOME", nullable = false, length = 200)
    private String nome;
    @Column(name = "TXT_DEPARTAMENTO", nullable = true, length = 200)
    private String departamento;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AvaliacaoPaciente> getAvaliacoesPaciente() {
        return avaliacoesPaciente;
    }
    
    public void adicionaAvaliacaoPaciente(AvaliacaoPaciente avaliacaoPaciente) {
        if (this.avaliacoesPaciente == null) {
            this.avaliacoesPaciente = new ArrayList<>();
        }
        this.avaliacoesPaciente.add(avaliacaoPaciente);
        avaliacaoPaciente.setServico(this);
    }

    public boolean remover(AvaliacaoPaciente avaliacaoPaciente) {
        return avaliacoesPaciente.remove(avaliacaoPaciente);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Servico)) {
            return false;
        }
        Servico other = (Servico) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "sensorsus_02.jpa.Servico[ id=" + id + " ]";
    }
}
