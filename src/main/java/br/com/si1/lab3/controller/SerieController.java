package br.com.si1.lab3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.si1.lab3.entities.Serie;
import br.com.si1.lab3.services.SerieService;

@RestController
@RequestMapping("/serie")
public class SerieController {
	
	
	@Autowired
	private SerieService serieService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Serie> adicionar(@RequestBody Serie serie) {
		Serie serieAdicionada = serieService.adicionar(serie);
		return new ResponseEntity<>(serieAdicionada, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{serieID}", method = RequestMethod.DELETE)
	public ResponseEntity<Serie> remover(@PathVariable Integer serieId) {
		Serie serie = serieService.getSerie(serieId);
		Serie serieRemovida = serieService.excluir(serie);
		return new ResponseEntity<>(serieRemovida, HttpStatus.OK);
	}
	
	public ResponseEntity<Serie> atualizar(@RequestBody Serie serie, @PathVariable Integer serieId) {
		serie.setId(serieId);
		Serie serieAtualizada = serieService.atualizar(serie);
		return new ResponseEntity<>(serieAtualizada, HttpStatus.OK);
	}
}
