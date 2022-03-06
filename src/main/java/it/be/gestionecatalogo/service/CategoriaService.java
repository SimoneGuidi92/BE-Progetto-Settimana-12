package it.be.gestionecatalogo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.be.gestionecatalogo.exception.CategoriaException;
import it.be.gestionecatalogo.model.Autore;
import it.be.gestionecatalogo.model.Categoria;
import it.be.gestionecatalogo.model.Libro;
import it.be.gestionecatalogo.repository.AutoreRepository;
import it.be.gestionecatalogo.repository.CategoriaRepository;
import it.be.gestionecatalogo.repository.LibroRepository;

@Service
public class CategoriaService {

	@Autowired
	AutoreRepository autoreRepository;
	
	@Autowired
	LibroRepository libroRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	public void saveAllFromSet(Set<Categoria> set) {
		for (Categoria categoria : set) {
			categoriaRepository.save(categoria);
		}
	}
	
	public List<Categoria> findAllCategorie() {
		return categoriaRepository.findAll();
	}
	
	public Optional<Categoria> findbyIdCategoria(Long id) {
		return categoriaRepository.findById(id);
	}
	
	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public Categoria update(Long id, Categoria categoria) {
		Optional<Categoria> categoriaResult = categoriaRepository.findById(id);
		if(categoriaResult.isPresent()) {
			Categoria c = categoriaResult.get();
			c.setNome(categoria.getNome());
			categoriaRepository.save(c);
			return c;
		}
		else {
			throw new CategoriaException("Categoria non trovato");
		}
	}
	
	public void delete(Long id) {
		categoriaRepository.deleteById(id);
	}
	
	
	public List<Libro> findLibriByCategoriaId(Long id) {
		Optional<Categoria> find = categoriaRepository.findById(id);
		List<Libro> libriPerCategoria = new ArrayList<>();
		Categoria temp = find.get();
		List<Libro> allLibri = libroRepository.findAll();
		for(Libro l : allLibri) {
			if(l.getCategorie().contains(temp)) {
				libriPerCategoria.add(l);
			}
		}
		return libriPerCategoria;
		
	}
	
	public List<Libro> findLibriByCategorieId(Set<Long> id) {
		List<Categoria> find = categoriaRepository.findAllById(id);
		List<Libro> libriPerCategoria = new ArrayList<>();
		List<Libro> allLibri = libroRepository.findAll();
		for(Libro l : allLibri) {
			for(Categoria c : find) {
				if(l.getCategorie().contains(c)) {
					libriPerCategoria.add(l);
				}
			}
			
		}
		return libriPerCategoria;
		
	}
	
	
	
	
}
