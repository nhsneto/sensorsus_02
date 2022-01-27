package sensorsus_02.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_USUARIO")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DISC_USUARIO", discriminatorType = DiscriminatorType.STRING, length = 2)
public abstract class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @NotBlank(message = "{sensorsus_02.jpa.Usuario.nome.blank}")
    @Column(name = "TXT_NOME")
    protected String nome;
    
    @NotBlank(message = "{sensorsus_02.jpa.Usuario.email.blank}")
    @Email(message = "{sensorsus_02.jpa.Usuario.email}")
    @Column(name = "TXT_EMAIL")
    protected String email;
    
    @Past(message = "{sensorsus_02.jpa.Usuario.dataNascimento}")
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_NASCIMENTO")
    protected Date dataNascimento;
    
    @NotBlank(message = "{sensorsus_02.jpa.Usuario.login.blank}")
    @Size(max = 30, message = "{sensorsus_02.jpa.Usuario.login.size}")
    @Pattern(regexp = "[a-z]+[0-9]*", message = "{sensorsus_02.jpa.Usuario.login}")
    @Column(name = "TXT_LOGIN")
    private String login;
    
    @NotBlank(message = "{sensorsus_02.jpa.Usuario.senha.blank}")
    @Size(max = 15 ,message = "{sensorsus_02.jpa.Usuario.senha.size}")
    @Pattern(regexp = ".{4,15}", message = "{sensorsus_02.jpa.Usuario.senha}")
    @Column(name = "TXT_SENHA")
    protected String senha;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id);
        sb.append(", ");
        sb.append(this.nome);
        sb.append(", ");
        sb.append(this.email);
        sb.append(", ");
        sb.append(this.senha);

        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
}
