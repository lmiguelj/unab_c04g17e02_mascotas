/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unab.Models;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis
 */
@Entity
@Table(name = "facturas_lin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturasLin.findAll", query = "SELECT f FROM FacturasLin f"),
    @NamedQuery(name = "FacturasLin.findByNumeroSig", query = "SELECT f FROM FacturasLin f WHERE f.numeroSig = :numeroSig"),
    @NamedQuery(name = "FacturasLin.findByCantidad", query = "SELECT f FROM FacturasLin f WHERE f.cantidad = :cantidad"),
    @NamedQuery(name = "FacturasLin.findByImpuestos", query = "SELECT f FROM FacturasLin f WHERE f.impuestos = :impuestos"),
    @NamedQuery(name = "FacturasLin.findBySubtotal", query = "SELECT f FROM FacturasLin f WHERE f.subtotal = :subtotal"),
    @NamedQuery(name = "FacturasLin.findByNotas", query = "SELECT f FROM FacturasLin f WHERE f.notas = :notas")})
public class FacturasLin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numero_sig")
    private Integer numeroSig;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private BigDecimal cantidad;
    @Column(name = "impuestos")
    private BigDecimal impuestos;
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Column(name = "notas")
    private String notas;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "facturasLin")
    private FacturasEnc facturasEnc;
    @JoinColumn(name = "id_codigo", referencedColumnName = "id_codigo")
    @ManyToOne
    private Productos idCodigo;

    public FacturasLin() {
    }

    public FacturasLin(Integer numeroSig) {
        this.numeroSig = numeroSig;
    }

    public Integer getNumeroSig() {
        return numeroSig;
    }

    public void setNumeroSig(Integer numeroSig) {
        this.numeroSig = numeroSig;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public FacturasEnc getFacturasEnc() {
        return facturasEnc;
    }

    public void setFacturasEnc(FacturasEnc facturasEnc) {
        this.facturasEnc = facturasEnc;
    }

    public Productos getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Productos idCodigo) {
        this.idCodigo = idCodigo;
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
        if (!(object instanceof FacturasLin)) {
            return false;
        }
        FacturasLin other = (FacturasLin) object;
        if ((this.numeroSig == null && other.numeroSig != null) || (this.numeroSig != null && !this.numeroSig.equals(other.numeroSig))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unab.Models.FacturasLin[ numeroSig=" + numeroSig + " ]";
    }
    
}
