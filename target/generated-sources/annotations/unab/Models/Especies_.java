package unab.Models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unab.Models.Productos;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-12-05T22:48:48", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Especies.class)
public class Especies_ { 

    public static volatile SingularAttribute<Especies, String> descripcion;
    public static volatile CollectionAttribute<Especies, Productos> productosCollection;
    public static volatile SingularAttribute<Especies, Integer> idEspecie;

}