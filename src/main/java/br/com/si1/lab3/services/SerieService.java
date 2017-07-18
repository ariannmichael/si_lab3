package br.com.si1.lab3.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.si1.lab3.entities.Serie;
import br.com.si1.lab3.repositories.SerieRepository;

@Service
public class SerieService {
	
	@Autowired
	private SerieRepository serieRepository;
	
	public Serie adicionar(Serie serie) {
		return serieRepository.save(serie);
	}
	
	public Serie atualizar(Serie serie) {
		return serieRepository.save(serie);
	}
	
	public Serie excluir(Serie serie) {
		serieRepository.delete(serie);
		return serie;
	}
	
	public Serie excluir(Integer serieID) {
		Serie serie = getSerie(serieID);
		serieRepository.delete(serie);
		return serie;
	}
	
	public Serie getSerie(Integer serieId) {
		return serieRepository.findOne(serieId);
	}
	
	public List<Serie> getSeries() {
		return serieRepository.findAll();
	}
	
	public List<Serie> seriesDoUsuario(Integer usuarioId) {
		List<Serie> series = this.getSeries();
		List<Serie> seriesDoUsario = new ArrayList<Serie>();
		for(Serie serie : series) {
			if(serie.getUsarioId().equals(usuarioId)) {
				seriesDoUsario.add(serie);
			}
		}
		
		return seriesDoUsario;
	}
}
