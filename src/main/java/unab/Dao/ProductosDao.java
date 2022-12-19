
package unab.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unab.Models.Productos;

@Repository
public interface ProductosDao extends CrudRepository <Productos, String>{
    
}
