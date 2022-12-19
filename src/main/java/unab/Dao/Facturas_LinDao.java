
package unab.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unab.Models.Facturas_Lin;

@Repository
public interface Facturas_LinDao extends CrudRepository <Facturas_Lin, String>{
    
}
