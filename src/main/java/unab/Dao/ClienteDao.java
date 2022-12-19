
package unab.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unab.Models.Cliente;

@Repository
public interface ClienteDao extends CrudRepository <Cliente, String>{
    
}
