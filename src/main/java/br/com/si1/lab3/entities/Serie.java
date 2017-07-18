package br.com.si1.lab3.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Serie {
	@Id
	@GeneratedValue
	private Integer id;
	private String imdbId;
	private String avaliacao;
	private String ultimoEpisodio;
	private Integer usarioId;
	private boolean noPerfil;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImdbId() {
		return imdbId;
	}
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	public String getAvaliacao() {
		return avaliacao;
	}
	public void setAvaliacao(String avaliacao) {
		this.avaliacao = avaliacao;
	}
	public String getUltimoEpisodio() {
		return ultimoEpisodio;
	}
	public void setUltimoEpisodio(String ultimoEpisodio) {
		this.ultimoEpisodio = ultimoEpisodio;
	}
	public Integer getUsarioId() {
		return usarioId;
	}
	public void setUsarioId(Integer usarioId) {
		this.usarioId = usarioId;
	}
	public boolean isNoPerfil() {
		return noPerfil;
	}
	public void setNoPerfil(boolean noPerfil) {
		this.noPerfil = noPerfil;
	}
	
	
	
}
