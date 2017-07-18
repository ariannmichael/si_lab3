package br.com.si1.lab3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.si1.lab3.entities.Serie;
import br.com.si1.lab3.entities.Usuario;
import br.com.si1.lab3.services.SerieService;
import br.com.si1.lab3.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private SerieService serieService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Usuario> adicionar(@RequestBody Usuario usuario) {
		Usuario novoUsuario = usuarioService.adicionar(usuario);
		if(novoUsuario == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED); 
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<Usuario> logar(@RequestBody Usuario usuario) {
		usuario = usuarioService.logar(usuario);
		if(usuario == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}
	
	public ResponseEntity<List<Serie>> consultarSeries(@PathVariable Integer usuarioId) {
		List<Serie> series = serieService.seriesDoUsuario(usuarioId);
		return new ResponseEntity<>(series, HttpStatus.OK);
	}
}
