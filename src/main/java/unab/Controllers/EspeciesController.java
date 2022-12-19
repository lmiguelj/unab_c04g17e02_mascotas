package unab.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unab.Dao.EspeciesDao;
import unab.Models.Especies;
import unab.Services.EspeciesService;


@RestController
@CrossOrigin("*")
@RequestMapping("/especies")
public class EspeciesController {
    @Autowired
    private EspeciesDao especiesDao;
    
    @Autowired
    private EspeciesService especiesService;
    
    @PostMapping(value = "/")
    public ResponseEntity<Especies> agregar(@RequestBody Especies especies){
        Especies obj = especiesService.save(especies);
        return new ResponseEntity<>(obj, HttpStatus.OK); 
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Especies> eliminar(@PathVariable String id){
        Especies obj = especiesService.findById(id);
        if (obj != null)
            especiesService.delete(id);
        else
            return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
    
    @PutMapping(value = "/")
    public ResponseEntity<Especies> editar(@RequestBody Especies especies) {
        Especies obj = especiesService.findById(especies.getId_especie());
        if (obj != null) {
            //obj.setDescripcion_categoria(categorias.setDescripcion_categoria());
            especiesService.save(obj);
        }else
            return new ResponseEntity<>(obj,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(obj, HttpStatus.OK);        
    }
    
    @GetMapping("/list")
    public List<Especies> consultarTodo(){
        return especiesService.findByAll();
    }
    
    @GetMapping("/list/{id}")
    public Especies consultarPorId(@PathVariable String id){
        return especiesService.findById(id);
    }
}
