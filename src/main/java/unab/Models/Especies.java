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
@Table(name="especies")
public class Especies implements Serializable {
    @Id
    @Column(name="id_especie")
    private String id_especie;
    @Column(name="descripcion")
    private String descripcion;
    
    @Override
    public String toString(){
        return "Especie [id_especie = " + id_especie + ", descripcion = " + descripcion + "]";
    }
    
}
