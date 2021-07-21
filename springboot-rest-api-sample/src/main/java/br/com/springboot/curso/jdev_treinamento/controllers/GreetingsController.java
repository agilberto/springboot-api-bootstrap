package br.com.springboot.curso.jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

import br.com.springboot.curso.jdev_treinamento.model.Usuario;
import br.com.springboot.curso.jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /* IC/CD ou CDI injeção de dependência*/
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot Api " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET )
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario);
    	
    	return "Olá Mundo " + nome;
    }
    
    @GetMapping(value = "listatodos")
    @ResponseBody /* Retorna os dados para o corpo da resposta */
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	
    		List<Usuario> usuarios = usuarioRepository.findAll(); // Execulta a consulta no banco de dados
    		
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); // Retorna a lista em Json
    	
    }
    
    @PostMapping(value = "salvar") // Mapeia a url
    @ResponseBody // Descirção da resposta
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){ // Recebe os dados para salvar
    
    Usuario user = usuarioRepository.save(usuario);
    
    return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    	
    	
    }
    
    @DeleteMapping(value = "delete") // Mapeia a url
    @ResponseBody // Descirção da resposta
    public ResponseEntity<String> delete(@RequestParam Long iduser){
    
    usuarioRepository.deleteById(iduser);
    
    return new ResponseEntity<String>("Usuario deletado com sucesso ", HttpStatus.OK);
    	
    	
    }
    
    @GetMapping(value = "buscaruserid") // Mapeia a url
    @ResponseBody // Descirção da resposta
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser){
    
    Usuario usuario = usuarioRepository.findById(iduser).get();
    
    return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    	
    	
    }
    
    @PutMapping(value = "atualizar") // Mapeia a url
    @ResponseBody // Descirção da resposta
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){ // Recebe os dados para salvar
    
    	if(usuario.getId() == null) {
    		 return new ResponseEntity<String>("id não foi informado para a atualização", HttpStatus.CREATED);
    	}
    	
    Usuario user = usuarioRepository.saveAndFlush(usuario);
    
    return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    	
    }
    
    @GetMapping(value = "buscarPorNome") // Mapeia a url
    @ResponseBody // Descirção da resposta
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name){
    
    List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
    
    return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    	
    
    }
    
}
