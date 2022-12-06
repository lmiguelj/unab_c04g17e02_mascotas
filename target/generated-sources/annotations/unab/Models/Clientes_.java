package unab.Models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unab.Models.FacturasEnc;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-12-05T22:48:48", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Clientes.class)
public class Clientes_ { 

    public static volatile CollectionAttribute<Clientes, FacturasEnc> facturasEncCollection;
    public static volatile SingularAttribute<Clientes, Integer> idCliente;
    public static volatile SingularAttribute<Clientes, String> ciudad;
    public static volatile SingularAttribute<Clientes, String> direccion;
    public static volatile SingularAttribute<Clientes, Integer> telefono;
    public static volatile SingularAttribute<Clientes, String> nombre;
    public static volatile SingularAttribute<Clientes, String> email;
    public static volatile SingularAttribute<Clientes, Short> activo;

}