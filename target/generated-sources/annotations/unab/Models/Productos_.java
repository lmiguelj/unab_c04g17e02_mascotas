package unab.Models;

import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unab.Models.Categorias;
import unab.Models.Especies;
import unab.Models.FacturasLin;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-12-05T22:48:48", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Productos.class)
public class Productos_ { 

    public static volatile SingularAttribute<Productos, String> descripcion;
    public static volatile CollectionAttribute<Productos, FacturasLin> facturasLinCollection;
    public static volatile SingularAttribute<Productos, BigDecimal> porcentajeImpuestos;
    public static volatile SingularAttribute<Productos, BigDecimal> precioVenta;
    public static volatile SingularAttribute<Productos, Categorias> idCategoria;
    public static volatile SingularAttribute<Productos, BigDecimal> costoUnitario;
    public static volatile SingularAttribute<Productos, Especies> idEspecie;
    public static volatile SingularAttribute<Productos, Integer> idCodigo;
    public static volatile SingularAttribute<Productos, Short> activo;

}