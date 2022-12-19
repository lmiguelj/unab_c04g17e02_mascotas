
package unab.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unab.Dao.UsuariosDao;
import unab.Models.Usuarios;

@Service
public class UsuariosService {
    @Autowired
    private UsuariosDao usuariosDao;
    
    @Transactional(readOnly=false)
    public Usuarios save(Usuarios usuarios){
        return usuariosDao.save(usuarios);
    }
    
    @Transactional(readOnly=false)
    public void delete(String id){
        usuariosDao.deleteById(id);
    }
    
    @Transactional(readOnly=true)
    public Usuarios findById(String id){
        return usuariosDao.findById(id).orElse(null);
    }
    
    @Transactional(readOnly=true)
    public List<Usuarios> findByAll(){
        return (List<Usuarios>) usuariosDao.findAll();
    }
}
