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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_ESTABELECIMENTOS_SERVICOS", 
            joinColumns = { @JoinColumn(name = "ID_ESTABELECIMENTO") }, 
            inverseJoinColumns = { @JoinColumn(name = "ID_SERVICO") })
    private List<Servico> servicos;
    
    // TODO implementar relacionamento com AvaliacaoPaciente
    
    // TODO implementar relacionamento com AvaliacaoProfissional

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

    public List<Servico> getServicos() {
        return servicos;
    }
    
    public void adicionaServico(Servico servico) {
        if (this.servicos == null) {
            this.servicos = new ArrayList<>();
        }
        servicos.add(servico);
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
}
