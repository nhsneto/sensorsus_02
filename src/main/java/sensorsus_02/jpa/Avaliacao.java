package sensorsus_02.jpa;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TB_AVALIACAO")
@NamedQueries(
    {
        @NamedQuery(
            name = "Avaliacao.SemUsuario",
            query = "SELECT a FROM Avaliacao a WHERE a.usuario IS NULL"
        ),
        @NamedQuery(
            name = "Avaliacao.ComUsuariosJoinFetch",
            query = "SELECT a FROM Avaliacao a JOIN FETCH a.usuario"
        ),
        @NamedQuery(
            name = "Avaliacao.UsuariosAcimaDe60Anos",
            query = "SELECT a FROM Avaliacao a WHERE a.usuario.dataNascimento <= ?1"
        ),
        @NamedQuery(
            name = "Avaliacao.QuantidadeTotal",
            query = "SELECT COUNT(a) FROM Avaliacao a"
        )
    }
)
public class Avaliacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_ESTABELECIMENTO", referencedColumnName = "ID")
    private Estabelecimento estabelecimento;
    
    @Column(name = "TXT_COMENTARIO", nullable = false, length = 255)
    private String comentario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Avaliacao)) {
            return false;
        }
        Avaliacao other = (Avaliacao) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "sensorsus_02.jpa.Avaliacao[ id=" + id + " ]";
    }
}
