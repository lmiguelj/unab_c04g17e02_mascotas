package unab.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unab.Dao.EspeciesDao;
import unab.Models.Especies;


@Service
public class EspeciesService {
    
    @Autowired
    private EspeciesDao especiesDao;
    
    @Transactional(readOnly=false)
    public Especies save(Especies especie){
        return especiesDao.save(especie);
    }
    
    @Transactional(readOnly=false)
    public void delete(String id){
        especiesDao.deleteById(id);
    }
    
    @Transactional(readOnly=true)
    public Especies findById(String id){
        return especiesDao.findById(id).orElse(null);
    }
    
    @Transactional(readOnly=true)
    public List<Especies> findByAll(){
        return (List<Especies>) especiesDao.findAll();
    }
}
