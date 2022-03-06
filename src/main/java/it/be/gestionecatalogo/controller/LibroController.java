package it.be.gestionecatalogo.controller;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.be.gestionecatalogo.model.Autore;
import it.be.gestionecatalogo.model.Libro;
import it.be.gestionecatalogo.service.AutoreService;
import it.be.gestionecatalogo.service.CategoriaService;
import it.be.gestionecatalogo.service.LibroService;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api")
public class LibroController {

	@Autowired
	private LibroService libroService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private AutoreService autoreService;

	// PER POTER ACCEDERE ALLA PAGINA SWAGGER BISOGNA INSERIRE QUESTO URL: localhost:8080/swagger-ui.html
	
	
	@Operation(summary = "Mostra la lista di tutti i libri presenti")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(path = "/libri")
	public ResponseEntity <List<Libro>> findAllLibri() {
		List<Libro> findAll = libroService.findAllLibri();
		if(!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@Operation(summary = "Permette di cercare un libro tramite il suo id")
	@GetMapping(path = "/libro/{id}")
	public ResponseEntity<Libro> findByIdLibro(@PathVariable Long id) {
		Optional<Libro> findLibro = libroService.findByIdLibro(id);
		return new ResponseEntity<>(findLibro.get(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Permette di salvare un nuovo libro")
	@PostMapping(path = "/libro")
	public ResponseEntity<Libro> save(@RequestBody Libro libro) {
		autoreService.saveAllFromSet(libro.getAutori());
		categoriaService.saveAllFromSet(libro.getCategorie());
		Libro save = libroService.save(libro);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}
	
	// metodo per modificare un libro, bisogna passargli l'id del libro da modificare
	// è possibile assegnargli un altro autore e categoria presenti nel database assegnandogli l'id corrispondente 
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Permette di modificare un libro passandogli l'id")
	@PutMapping(path = "/libro/{id}")
	public ResponseEntity<Libro> update(@PathVariable Long id, @RequestBody Libro libro) {
		Libro save = libroService.update(id, libro);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Permette di cancellare un libro tramite l'id")
	@DeleteMapping(path = "/libro/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		libroService.delete(id);
		return new ResponseEntity<>("Libro cancellato", HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@Operation(summary = "Permette di cercare i libri scritti da un determinato autore inserendo l'id dell'autore")
	@GetMapping(path = "/libriperautore/{id}")
	public ResponseEntity <List<Libro>> findLibriByAutoreId(@PathVariable Long id) {
		Optional<Autore> a = autoreService.findbyIdAutore(id);
		if(a.isPresent()) {
			List<Libro> result = libroService.findLibriByAutoreId(id);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@Operation(summary = "Permette di cercare i libri scritti da uno o più autori inserendo l'id degli autori")
	@GetMapping(path = "/libriperautori/{id}")
	public ResponseEntity<List<Libro>> findLibriByAutoriId(@PathVariable Set<Long> id) {
		List<Libro> result = libroService.findLibriByAutoriId(id);
		if(!result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
}
