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
//import unab.Dao.ClienteDao;
import unab.Models.Cliente;
import unab.Services.ClienteService;

@RestController
@CrossOrigin("*")
@RequestMapping("/cliente")
public class ClienteController {
    //@Autowired
    //private ClienteDao clienteDao;
    
    @Autowired
    private ClienteService clienteService;
    
    @PostMapping(value = "/")
    public ResponseEntity<Cliente> agregar(@RequestBody Cliente cliente){
        Cliente obj = clienteService.save(cliente);
        return new ResponseEntity<>(obj, HttpStatus.OK); 
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Cliente> eliminar(@PathVariable String id){
        Cliente obj = clienteService.findById(id);
        if (obj != null)
            clienteService.delete(id);
        else
            return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
    
    @PutMapping(value = "/")
    public ResponseEntity<Cliente> editar(@RequestBody Cliente cliente) {
        Cliente obj = clienteService.findById(cliente.getId_cliente());
        if (obj != null) {
            //obj.setDescripcion_categoria(categorias.setDescripcion_categoria());
            clienteService.save(obj);
        }else
            return new ResponseEntity<>(obj,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(obj, HttpStatus.OK);        
    }
    
    @GetMapping("/list")
    public List<Cliente> consultarTodo(){
        return clienteService.findByAll();
    }
    
    @GetMapping("/list/{id}")
    public Cliente consultarPorId(@PathVariable String id){
        return clienteService.findById(id);
    }
    
}
