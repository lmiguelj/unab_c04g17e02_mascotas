
package unab.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unab.Models.Especies;

@Repository
public interface EspeciesDao extends CrudRepository <Especies, String>{
    
}
