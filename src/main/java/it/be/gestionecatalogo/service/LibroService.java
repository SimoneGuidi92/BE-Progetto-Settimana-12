package it.be.gestionecatalogo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.be.gestionecatalogo.exception.LibroException;
import it.be.gestionecatalogo.model.Autore;
import it.be.gestionecatalogo.model.Libro;
import it.be.gestionecatalogo.repository.AutoreRepository;
import it.be.gestionecatalogo.repository.LibroRepository;

@Service
public class LibroService {

	@Autowired
	LibroRepository libroRepository;
	
	@Autowired
	AutoreRepository autoreRepository;
	

	
	public List<Libro> findAllLibri() {
		return libroRepository.findAll();
	}
	
	public Optional<Libro> findByIdLibro(Long id) {
		return libroRepository.findById(id);
	}
	
	public Libro save(Libro libro) {
		return libroRepository.save(libro);
	}
	
	public Libro update(Long id, Libro libro) {
		Optional<Libro> libroResult = libroRepository.findById(id);
		if(libroResult.isPresent()) {
			Libro libroUpdate = libroResult.get();
			libroUpdate.setAnnoPubblicazione(libro.getAnnoPubblicazione());
			libroUpdate.setAutori(libro.getAutori());
			libroUpdate.setCategorie(libro.getCategorie());
			libroUpdate.setPrezzo(libro.getPrezzo());
			libroUpdate.setTitolo(libro.getTitolo());
			libroRepository.save(libroUpdate);
			return libroUpdate;
		}
		else {
			throw new LibroException("Il libro non è stato aggiornato");
		}
	}
	
	public void delete(Long id) {
		libroRepository.deleteById(id);
	}
	
	
	public void deleteLibriNoAutori() {
		List<Libro> all = libroRepository.findAll();
		List<Libro> libriNoAutori = new ArrayList<>();
		for(Libro libro : all) {
			if(libro.getAutori().isEmpty()) {
				libriNoAutori.add(libro);
			}
		}
		libroRepository.deleteAll(libriNoAutori);
		
	}
	
	public List<Libro> findLibriByAutoreId(Long id) {
		Optional<Autore> find = autoreRepository.findById(id);
		List<Libro> libriPerAutore = new ArrayList<>();
		Autore temp = find.get();
		List<Libro> allLibri = libroRepository.findAll();
		for(Libro l : allLibri) {
			if(l.getAutori().contains(temp)) {
				libriPerAutore.add(l);
			}
		}
		return libriPerAutore;
		
	}
	
	public List<Libro> findLibriByAutoriId(Set<Long> id) {
		List<Autore> find = autoreRepository.findAllById(id);
		List<Libro> libriPerAutore = new ArrayList<>();
		List<Libro> allLibri = libroRepository.findAll();
		for(Libro l : allLibri) {
			for(Autore a : find) {
				if(l.getAutori().contains(a)) {
					libriPerAutore.add(l);
				}
			}
			
		}
		return libriPerAutore;
		
	}
	
	
	
	
	
}
