/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unab.Models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis
 */
@Entity
@Table(name = "facturas_enc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturasEnc.findAll", query = "SELECT f FROM FacturasEnc f"),
    @NamedQuery(name = "FacturasEnc.findByNumeroSig", query = "SELECT f FROM FacturasEnc f WHERE f.numeroSig = :numeroSig"),
    @NamedQuery(name = "FacturasEnc.findByFecha", query = "SELECT f FROM FacturasEnc f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "FacturasEnc.findByObservaciones", query = "SELECT f FROM FacturasEnc f WHERE f.observaciones = :observaciones"),
    @NamedQuery(name = "FacturasEnc.findBySubtotal", query = "SELECT f FROM FacturasEnc f WHERE f.subtotal = :subtotal"),
    @NamedQuery(name = "FacturasEnc.findByImpuestos", query = "SELECT f FROM FacturasEnc f WHERE f.impuestos = :impuestos"),
    @NamedQuery(name = "FacturasEnc.findByValorTotal", query = "SELECT f FROM FacturasEnc f WHERE f.valorTotal = :valorTotal")})
public class FacturasEnc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numero_sig")
    private Integer numeroSig;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "observaciones")
    private String observaciones;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Column(name = "impuestos")
    private BigDecimal impuestos;
    @Column(name = "valor_total")
    private BigDecimal valorTotal;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Clientes idCliente;
    @JoinColumn(name = "numero_sig", referencedColumnName = "numero_sig", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private FacturasLin facturasLin;

    public FacturasEnc() {
    }

    public FacturasEnc(Integer numeroSig) {
        this.numeroSig = numeroSig;
    }

    public Integer getNumeroSig() {
        return numeroSig;
    }

    public void setNumeroSig(Integer numeroSig) {
        this.numeroSig = numeroSig;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Clientes getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Clientes idCliente) {
        this.idCliente = idCliente;
    }

    public FacturasLin getFacturasLin() {
        return facturasLin;
    }

    public void setFacturasLin(FacturasLin facturasLin) {
        this.facturasLin = facturasLin;
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
        if (!(object instanceof FacturasEnc)) {
            return false;
        }
        FacturasEnc other = (FacturasEnc) object;
        if ((this.numeroSig == null && other.numeroSig != null) || (this.numeroSig != null && !this.numeroSig.equals(other.numeroSig))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unab.Models.FacturasEnc[ numeroSig=" + numeroSig + " ]";
    }
    
}
