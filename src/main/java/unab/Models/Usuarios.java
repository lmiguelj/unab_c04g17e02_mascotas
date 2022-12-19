
package unab.Models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="usuarios")
public class Usuarios implements Serializable{
    @Id
    @Column(name="id_usuario")
    private String id_usuario;
    @Column(name="nombre")
    private String nombre;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="es_admin")
    private String es_admin;
        
    
    @Override
    public String toString(){
        return "Usuario [id_usuario = " + id_usuario + ", nombre = " + nombre
                + ", email = " + email + ", password = " + password + ", es_admin = " + es_admin + "]";
    }
}
