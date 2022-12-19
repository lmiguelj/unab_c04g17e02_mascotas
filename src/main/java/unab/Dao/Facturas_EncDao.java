
package unab.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unab.Models.Facturas_Enc;

@Repository
public interface Facturas_EncDao extends CrudRepository <Facturas_Enc, String>{
    
}
