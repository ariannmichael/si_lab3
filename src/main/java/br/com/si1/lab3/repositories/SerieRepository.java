package br.com.si1.lab3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.si1.lab3.entities.Serie;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Integer>{

}
