package unab.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unab.Dao.ProductosDao;
import unab.Models.Productos;

@Service
public class ProductosService {
    @Autowired
    private ProductosDao productosDao;
    
    @Transactional(readOnly=false)
    public Productos save(Productos productos){
        return productosDao.save(productos);
    }
    
    @Transactional(readOnly=false)
    public void delete(String id){
        productosDao.deleteById(id);
    }
    
    @Transactional(readOnly=true)
    public Productos findById(String id){
        return productosDao.findById(id).orElse(null);
    }
    
    @Transactional(readOnly=true)
    public List<Productos> findByAll(){
        return (List<Productos>) productosDao.findAll();
    }
}
