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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ESTABELECIMENTO")
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
    
    @ElementCollection
    @CollectionTable(name = "TB_TELEFONE", joinColumns = @JoinColumn(name = "ID_ESTABELECIMENTO",
            nullable = false))
    @Column(name = "TXT_TELEFONE", nullable = true, length = 20)
    private Collection<String> telefones;
    
    @Column(name = "TXT_NOME", nullable = false, length = 255)
    private String nome;
    @Column(name = "TXT_CODIGO_CNES", nullable = false, unique = true, length = 40)
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
