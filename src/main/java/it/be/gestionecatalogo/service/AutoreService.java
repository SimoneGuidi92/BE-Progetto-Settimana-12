package it.be.gestionecatalogo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.be.gestionecatalogo.exception.AutoreException;
import it.be.gestionecatalogo.model.Autore;
import it.be.gestionecatalogo.repository.AutoreRepository;
import it.be.gestionecatalogo.repository.LibroRepository;

@Service
public class AutoreService {
	
	@Autowired
	AutoreRepository autoreRepository;
	
	@Autowired
	LibroRepository libroRepository;
	
	public void saveAllFromSet(Set<Autore> set) {
		for (Autore autore : set) {
			autoreRepository.save(autore);
		}
	}
	
	
	public List<Autore> findAllAutori() {
		return autoreRepository.findAll();
	}
	
	public Optional<Autore> findbyIdAutore(Long id) {
		return autoreRepository.findById(id);
	}
	
	public Autore save(Autore autore) {
		return autoreRepository.save(autore);
	}
	
	public Autore update(Long id, Autore autore) {
		Optional<Autore> autoreResult = autoreRepository.findById(id);
		if(autoreResult.isPresent()) {
			Autore a = autoreResult.get();
			a.setNome(autore.getNome());
			a.setCognome(autore.getCognome());
			autoreRepository.save(a);
			return a;
		}
		else {
			throw new AutoreException("Autore non trovato");
		}
	}
	
	public void delete(Long id) {
		autoreRepository.deleteById(id);
	}
	
	
	
	
	
}
