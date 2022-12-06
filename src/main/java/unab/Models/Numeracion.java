/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unab.Models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis
 */
@Entity
@Table(name = "numeracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Numeracion.findAll", query = "SELECT n FROM Numeracion n"),
    @NamedQuery(name = "Numeracion.findByNumeroSig", query = "SELECT n FROM Numeracion n WHERE n.numeroSig = :numeroSig")})
public class Numeracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numero_sig")
    private Integer numeroSig;

    public Numeracion() {
    }

    public Numeracion(Integer numeroSig) {
        this.numeroSig = numeroSig;
    }

    public Integer getNumeroSig() {
        return numeroSig;
    }

    public void setNumeroSig(Integer numeroSig) {
        this.numeroSig = numeroSig;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroSig != null ? numeroSig.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Numeracion)) {
            return false;
        }
        Numeracion other = (Numeracion) object;
        if ((this.numeroSig == null && other.numeroSig != null) || (this.numeroSig != null && !this.numeroSig.equals(other.numeroSig))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unab.Models.Numeracion[ numeroSig=" + numeroSig + " ]";
    }
    
}
