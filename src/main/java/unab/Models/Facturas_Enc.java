
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
@Table(name="facturas_enc")
public class Facturas_Enc implements Serializable{
    @Id
    @Column(name="numero_sig")
    private String numero_sig;
    @Column(name="fecha")
    private String fecha;
    @Column(name="observaciones")
    private String observaciones;
    @Column(name="subtotal")
    private String subtotal;
    @Column(name="impuestos")
    private String impuestos;
    @Column(name="valor_total")
    private String valor_total;
    
    @Override
    public String toString(){
        return "Categoria [numero_sig = " + numero_sig + ", fecha = " + fecha
                + ", observaciones = " + observaciones + ", subtotal = " + subtotal
                + ", impuestos = " + impuestos + ", valor_total = " + valor_total + "]";
    }
}
