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
import unab.Models.Usuarios;
import unab.Services.UsuariosService;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuariosController {
    //@Autowired
    //private UsuariosDao usuariosDao;
    
    @Autowired
    private UsuariosService usuariosService;
    
    @PostMapping(value = "/")
    public ResponseEntity<Usuarios> agregar(@RequestBody Usuarios usuarios){
        Usuarios obj = usuariosService.save(usuarios);
        return new ResponseEntity<>(obj, HttpStatus.OK); 
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Usuarios> eliminar(@PathVariable String id){
        Usuarios obj = usuariosService.findById(id);
        if (obj != null)
            usuariosService.delete(id);
        else
            return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
    
    @PutMapping(value = "/")
    public ResponseEntity<Usuarios> editar(@RequestBody Usuarios usuarios) {
        Usuarios obj = usuariosService.findById(usuarios.getId_usuario());
        if (obj != null) {
            //obj.setDescripcion_categoria(categorias.setDescripcion_categoria());
            usuariosService.save(obj);
        }else
            return new ResponseEntity<>(obj,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(obj, HttpStatus.OK);        
    }
    
    @GetMapping("/list")
    public List<Usuarios> consultarTodo(){
        return usuariosService.findByAll();
    }
    
    @GetMapping("/list/{id}")
    public Usuarios consultarPorId(@PathVariable String id){
        return usuariosService.findById(id);
    }
}
