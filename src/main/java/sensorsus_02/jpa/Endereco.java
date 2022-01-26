package sensorsus_02.jpa;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_ENDERECO")
@NamedQueries(
    {
        @NamedQuery(
            name = "Endereco.PorBairro",
            query = "SELECT e FROM Endereco e WHERE e.bairro = :bairro"
        ),
        @NamedQuery(
            name = "Endereco.avaliacoesPorBairro",
            query = "SELECT e.bairro, COUNT(a) FROM Endereco e, Avaliacao a "
                    + "WHERE a.estabelecimento.endereco.numero = e.numero "
                    + "GROUP BY e ORDER BY e.bairro"
        ),
        @NamedQuery(
            name = "Endereco.SemEstabelecimento",
            query = "SELECT e FROM Endereco e WHERE e "
                    + "NOT IN (SELECT e FROM Endereco e JOIN Estabelecimento es)"
        ),
        @NamedQuery(
            name = "Endereco.PorAvaliacao",
            query = "SELECT e FROM Endereco e JOIN Estabelecimento es ON e.id = es.id "
                    + "WHERE :avaliacao MEMBER OF es.avaliacoes"
        ),
        @NamedQuery(
            name = "Endereco.NaoPertencentePorCidade",
            query = "SELECT e FROM Endereco e WHERE LOWER(e.cidade) <> :cidade"
        )
    }
)
public class Endereco implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(mappedBy = "endereco", optional = false, cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY)
    private Estabelecimento estabelecimento;

    @NotBlank(message = "sensorsus_02.jpa.Endereco.estado.blank")
    @Size(max = 80, message = "{sensorsus_02.jpa.Endereco.estado.max}")
    @ValidaEstado
    @Column(name = "TXT_ESTADO")
    private String estado;
    
    @NotBlank(message = "{sensorsus_02.jpa.Endereco.cidade.blank}")
    @Size(max = 150, message = "{sensorsus_02.jpa.Endereco.cidade.max}")
    @Column(name = "TXT_CIDADE")
    private String cidade;
    
    @NotBlank(message = "{sensorsus_02.jpa.Endereco.bairro.blank}")
    @Size(max = 150, message = "{sensorsus_02.jpa.Endereco.bairro.max}")
    @Column(name = "TXT_BAIRRO")
    private String bairro;
    
    @NotBlank(message = "{sensorsus_02.jpa.Endereco.logradouro.blank}")
    @Size(max = 255, message = "{sensorsus_02.jpa.Endereco.logradouro.max}")
    @Column(name = "TXT_LOGRADOURO")
    private String logradouro;
    
    @NotNull(message = "{sensorsus_02.jpa.Endereco.numero.null}")
    @Min(value = 1, message = "{sensorsus_02.jpa.Endereco.numero.min}")
    @Max(value = 99999, message = "{sensorsus_02.jpa.Endereco.numero.max}")
    @Column(name = "INT_NUMERO")
    private Integer numero;
    
    @Size(max = 255, message = "{sensorsus_02.jpa.Endereco.complemento.max}")
    @Column(name = "TXT_COMPLEMENTO")
    private String complemento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Endereco)) {
            return false;
        }
        Endereco other = (Endereco) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "sensorsus_02.jpa.Endereco[ id=" + id + " ]";
    }
}
