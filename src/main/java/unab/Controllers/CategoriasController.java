package unab.Controllers;

import unab.Models.Categorias;
import unab.Dao.CategoriasDao;
import unab.Services.CategoriasService;

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

@RestController
@CrossOrigin("*")
@RequestMapping("/categorias")
public class CategoriasController {
    
    @Autowired
    private CategoriasDao categoriasDao;
    
    @Autowired
    private CategoriasService categoriasService;
    
    @PostMapping(value = "/")
    public ResponseEntity<Categorias> agregar(@RequestBody Categorias categorias){
        Categorias obj = categoriasService.save(categorias);
        return new ResponseEntity<>(obj, HttpStatus.OK); 
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Categorias> eliminar(@PathVariable String id){
        Categorias obj = categoriasService.findById(id);
        if (obj != null)
            categoriasService.delete(id);
        else
            return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
    
    @PutMapping(value = "/")
    public ResponseEntity<Categorias> editar(@RequestBody Categorias categorias) {
        Categorias obj = categoriasService.findById(categorias.getId_categoria());
        if (obj != null) {
            //obj.setDescripcion_categoria(categorias.setDescripcion_categoria());
            categoriasService.save(obj);
        }else
            return new ResponseEntity<>(obj,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(obj, HttpStatus.OK);        
    }
    
    @GetMapping("/list")
    public List<Categorias> consultarTodo(){
        return categoriasService.findByAll();
    }
    
    @GetMapping("/list/{id}")
    public Categorias consultarPorId(@PathVariable String id){
        return categoriasService.findById(id);
    }
}
