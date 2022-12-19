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
@Table(name="productos")
public class Productos implements Serializable{
    @Id
    @Column(name="id_codigo")
    private String id_codigo;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="id_categoria")
    private String id_categoria;
    @Column(name="id_especie")
    private String id_especie;
    @Column(name="costo_unitario")
    private String costo_unitario;
    @Column(name="porcentaje_impuestos")
    private String porcentaje_impuestos;
    @Column(name="precio_venta")
    private String precio_venta;
    @Column(name="activo")
    private String activo;
    
    
    @Override
    public String toString(){
        return "Categoria [id_codigo = " + id_codigo + ", descripcion = " + descripcion
                + ", id_categoria = " + id_categoria + ", id_especie = " + id_especie
                + ", costo_unitario = " + costo_unitario + ", porcentaje_impuestos = " + porcentaje_impuestos
                + ", precio_venta = " + precio_venta + ", activo = " + activo + "]";
    }
    
}
