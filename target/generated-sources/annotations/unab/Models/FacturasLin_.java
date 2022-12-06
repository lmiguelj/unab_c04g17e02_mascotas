package unab.Models;

import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unab.Models.FacturasEnc;
import unab.Models.Productos;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-12-05T22:48:48", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(FacturasLin.class)
public class FacturasLin_ { 

    public static volatile SingularAttribute<FacturasLin, BigDecimal> impuestos;
    public static volatile SingularAttribute<FacturasLin, BigDecimal> subtotal;
    public static volatile SingularAttribute<FacturasLin, String> notas;
    public static volatile SingularAttribute<FacturasLin, Integer> numeroSig;
    public static volatile SingularAttribute<FacturasLin, BigDecimal> cantidad;
    public static volatile SingularAttribute<FacturasLin, Productos> idCodigo;
    public static volatile SingularAttribute<FacturasLin, FacturasEnc> facturasEnc;

}