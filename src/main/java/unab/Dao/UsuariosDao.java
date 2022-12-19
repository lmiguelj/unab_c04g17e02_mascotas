
package unab.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unab.Models.Usuarios;

@Repository
public interface UsuariosDao extends CrudRepository <Usuarios, String>{
    
}
