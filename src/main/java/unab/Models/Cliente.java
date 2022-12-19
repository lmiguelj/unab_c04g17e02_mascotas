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
@Table(name="clientes")
public class Cliente implements Serializable{
    @Id
    @Column(name="id_cliente")
    private String id_cliente;
    @Column(name="nombre")
    private String nombre;
    @Column(name="direccion")
    private String direccion;
    @Column(name="telefono")
    private String telefono;
    @Column(name="ciudad")
    private String ciudad;
    @Column(name="email")
    private String email;
    @Column(name="activo")
    private String activo;
    
    
    @Override
    public String toString(){
        return "Clientes [id_cliente = " + id_cliente + ", nombre = " + nombre
                + ", direccion = " + direccion + ", telefono = " + telefono
                + ", ciudad = " + ciudad + ", email = " + email + ", activo = " + activo + "]";
    }
}
