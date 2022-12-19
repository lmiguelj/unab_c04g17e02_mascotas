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
@Table(name="categorias")
public class Categorias implements Serializable{
    @Id
    @Column(name="id_categoria")
    private String id_categoria;
    @Column(name="descripcion")
    private String descripcion;
    
    @Override
    public String toString(){
        return "Categoria [id_categoria = " + id_categoria + ", descripcion = " + descripcion + "]";
    }
}
