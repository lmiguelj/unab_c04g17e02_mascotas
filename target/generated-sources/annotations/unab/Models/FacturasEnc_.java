package unab.Models;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unab.Models.Clientes;
import unab.Models.FacturasLin;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-12-05T22:48:48", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(FacturasEnc.class)
public class FacturasEnc_ { 

    public static volatile SingularAttribute<FacturasEnc, Date> fecha;
    public static volatile SingularAttribute<FacturasEnc, Clientes> idCliente;
    public static volatile SingularAttribute<FacturasEnc, BigDecimal> subtotal;
    public static volatile SingularAttribute<FacturasEnc, BigDecimal> impuestos;
    public static volatile SingularAttribute<FacturasEnc, FacturasLin> facturasLin;
    public static volatile SingularAttribute<FacturasEnc, BigDecimal> valorTotal;
    public static volatile SingularAttribute<FacturasEnc, String> observaciones;
    public static volatile SingularAttribute<FacturasEnc, Integer> numeroSig;

}