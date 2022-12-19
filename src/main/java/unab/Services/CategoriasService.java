package unab.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unab.Dao.CategoriasDao;
import unab.Models.Categorias;

@Service
public class CategoriasService {
    
    @Autowired
    private CategoriasDao categoriasDao;
    
    @Transactional(readOnly=false)
    public Categorias save(Categorias categoria){
        return categoriasDao.save(categoria);
    }
    
    @Transactional(readOnly=false)
    public void delete(String id){
        categoriasDao.deleteById(id);
    }
    
    @Transactional(readOnly=true)
    public Categorias findById(String id){
        return categoriasDao.findById(id).orElse(null);
    }
    
    @Transactional(readOnly=true)
    public List<Categorias> findByAll(){
        return (List<Categorias>) categoriasDao.findAll();
    }
}
