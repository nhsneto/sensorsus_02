package sensorsus_02.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_ESTABELECIMENTO")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Estabelecimento.SemAvaliacoes",
                    query = "SELECT e FROM Estabelecimento e WHERE e.avaliacoes IS EMPTY"
            ),
            @NamedQuery(
                    name = "Estabelecimento.PorCidade",
                    query = "SELECT e FROM Estabelecimento e WHERE e.endereco.cidade = :cidade"
            ),
            @NamedQuery(
                    name = "Estabelecimento.MaiorNumeroDeAvaliacoes",
                    query = "SELECT e FROM Estabelecimento e WHERE SIZE(e.avaliacoes) = "
                            + "SELECT MAX(SIZE(e.avaliacoes)) FROM Estabelecimento e"
            ),
            @NamedQuery(
                    name = "Estabelecimento.PorLogradouroSubstring",
                    query = "SELECT e FROM Estabelecimento e "
                            + "WHERE SUBSTRING(e.endereco.logradouro, 1, 2) = 'Av' ORDER BY e.nome"
            ),
            @NamedQuery(
                    name = "Estabelecimento.PorTelefone",
                    query = "SELECT e FROM Estabelecimento e WHERE :telefone MEMBER OF e.telefones"
            ),
            @NamedQuery(
                    name = "Estabelecimento.QueNaoPossuiServicoEspecifico",
                    query = "SELECT e FROM Estabelecimento e "
                            + "WHERE :servico NOT MEMBER OF e.servicos ORDER BY e.nome"
            )
        }
)
public class Estabelecimento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ID_ENDERECO", referencedColumnName = "ID")
    private Endereco endereco;
    
    @OneToMany(mappedBy = "estabelecimento", fetch = FetchType.LAZY, cascade = CascadeType.ALL,
        orphanRemoval = true)
    private List<Avaliacao> avaliacoes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "TB_ESTABELECIMENTOS_SERVICOS", 
            joinColumns = { @JoinColumn(name = "ID_ESTABELECIMENTO") }, 
            inverseJoinColumns = { @JoinColumn(name = "ID_SERVICO") })
    private List<Servico> servicos;
    
    @Size(max = 3, message = "{sensorsus_02.jpa.Estabelecimento.telefones.max}")
    @ValidaTelefone
    @ElementCollection
    @CollectionTable(name = "TB_TELEFONE", joinColumns = @JoinColumn(name = "ID_ESTABELECIMENTO",
            nullable = false))
    @Column(name = "TXT_TELEFONE")
    private Collection<String> telefones;
    
    @NotBlank(message = "{sensorsus_02.jpa.Estabelecimento.nome.blank}")
    @Size(max = 255, message = "{sensorsus_02.jpa.Estabelecimento.nome.max}")
    @Column(name = "TXT_NOME")
    private String nome;
    
    @NotBlank(message = "{sensorsus_02.jpa.Estabelecimento.codigoCnes.blank}")
    @Pattern(regexp = "[0-9]{15}", message = "{sensorsus_02.jpa.Estabelecimento.codigoCnes}")
    @Size(min = 15, max = 15, message = "{sensorsus_02.jpa.Estabelecimento.codigoCnes.size}")
    @Column(name = "TXT_CODIGO_CNES")
    private String codigoCnes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
        this.endereco.setEstabelecimento(this);
    }
    
    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void adicionaAvaliacao(Avaliacao avaliacao) {
        if (this.avaliacoes == null) {
            this.avaliacoes = new ArrayList<>();
        }
        this.avaliacoes.add(avaliacao);
        avaliacao.setEstabelecimento(this);
    }

    public boolean removeAvaliacao(Avaliacao avaliacao) {
        return avaliacoes.remove(avaliacao);
    }

    public List<Servico> getServicos() {
        return servicos;
    }
    
    public void adicionaServico(Servico servico) {
        if (this.servicos == null) {
            this.servicos = new ArrayList<>();
        }
        servicos.add(servico);
    }
    
    public boolean removeServico(Servico servico) {
        return servicos.remove(servico);
    }
    
    public Collection<String> getTelefones() {
        return telefones;
    }
    
    public void adicionaTelefone(String telefone) {
        if (telefones == null) telefones = new HashSet<>();
        telefones.add(telefone);
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoCnes() {
        return codigoCnes;
    }

    public void setCodigoCnes(String codigoCnes) {
        this.codigoCnes = codigoCnes;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Estabelecimento)) {
            return false;
        }
        Estabelecimento other = (Estabelecimento) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "sensorsus_02.jpa.Estabelecimento[ id=" + id + " ]";
    }
}
