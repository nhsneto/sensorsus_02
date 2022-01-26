package sensorsus_02.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_SERVICO")
@NamedQueries(
    {
        @NamedQuery(
            name = "Servico.PorNome",
            query = "SELECT s FROM Servico s WHERE s.nome = :nome"
        ),
        @NamedQuery(
            name = "Servico.NumeroDeServicosPorDepartamento",
            query = "SELECT COUNT(s) FROM Servico s WHERE s.departamento = ?1"
        ),
        @NamedQuery(
            name = "Servico.PorBairro",
            query = "SELECT s FROM Servico s JOIN FETCH Estabelecimento es "
                    + "WHERE es.endereco.bairro = :bairro AND s MEMBER OF es.servicos"
        ),
        @NamedQuery(
            name = "Servico.NumeroTotal",
            query = "SELECT COUNT(s) FROM Servico s"
        ),
        @NamedQuery(
            name = "Servico.NaoContidosNoEstabelecimento",
            query = "SELECT s FROM Servico s JOIN FETCH Estabelecimento es "
                    + "WHERE es = :estabelecimento AND s NOT MEMBER OF es.servicos ORDER BY s.nome"
        )
    }
)
public class Servico implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "{sensorsus_02.jpa.Servico.nome.blank}")
    @Size(max = 255, message = "{sensorsus_02.jpa.Servico.nome.max}")
    @Column(name = "TXT_NOME")
    private String nome;
    
    @NotBlank(message = "{sensorsus_02.jpa.Servico.departamento.blank}")
    @Size(max = 255, message = "{sensorsus_02.jpa.Servico.departamento.max}")
    @Column(name = "TXT_DEPARTAMENTO")
    private String departamento;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
