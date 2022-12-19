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
import unab.Models.Productos;
import unab.Services.ProductosService;

@RestController
@CrossOrigin("*")
@RequestMapping("/productos")
public class ProductosController {
    //@Autowired
    //private ProductosDao productosDao;
    
    @Autowired
    private ProductosService productosService;
    
    @PostMapping(value = "/")
    public ResponseEntity<Productos> agregar(@RequestBody Productos productos){
        Productos obj = productosService.save(productos);
        return new ResponseEntity<>(obj, HttpStatus.OK); 
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Productos> eliminar(@PathVariable String id){
        Productos obj = productosService.findById(id);
        if (obj != null)
            productosService.delete(id);
        else
            return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
    
    @PutMapping(value = "/")
    public ResponseEntity<Productos> editar(@RequestBody Productos productos) {
        Productos obj = productosService.findById(productos.getId_codigo());
        if (obj != null) {
            //obj.setDescripcion_categoria(categorias.setDescripcion_categoria());
            productosService.save(obj);
        }else
            return new ResponseEntity<>(obj,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(obj, HttpStatus.OK);        
    }
    
    @GetMapping("/list")
    public List<Productos> consultarTodo(){
        return productosService.findByAll();
    }
    
    @GetMapping("/list/{id}")
    public Productos consultarPorId(@PathVariable String id){
        return productosService.findById(id);
    }
}
