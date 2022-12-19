
package unab.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unab.Models.Categorias;

@Repository
public interface CategoriasDao extends CrudRepository <Categorias, String>{
    
}
