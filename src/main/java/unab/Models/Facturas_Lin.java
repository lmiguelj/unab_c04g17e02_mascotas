
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
@Table(name="facturas_lin")
public class Facturas_Lin implements Serializable{
    @Id
    @Column(name="numero_sig")
    private String numero_sig;
    @Column(name="id_codigo")
    private String id_codigo;
    @Column(name="cantidad")
    private String cantidad;
    @Column(name="impuestos")
    private String impuestos;
    @Column(name="subtotal")
    private String subtotal;
    @Column(name="notas")
    private String notas;
    
    @Override
    public String toString(){
        return "Factura_Lin [numero_sig = " + numero_sig + ", id_codigo = " + id_codigo
                + ", cantidad = " + cantidad + ", impuestos = " + impuestos
                + ", subtotal = " + subtotal + ", notas = " + notas + "]";
    }
    
}
