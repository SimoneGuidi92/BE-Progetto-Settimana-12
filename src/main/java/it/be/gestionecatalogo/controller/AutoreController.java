package it.be.gestionecatalogo.controller;

import java.util.List;
import java.util.Optional;

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
import it.be.gestionecatalogo.service.LibroService;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api")
public class AutoreController {


	@Autowired
	private AutoreService autoreService;
	
	@Autowired
	private LibroService libroService;

	// PER POTER ACCEDERE ALLA PAGINA SWAGGER BISOGNA INSERIRE QUESTO URL: localhost:8080/swagger-ui.html

	
	@Operation(summary = "Mostra la lista di tutti gli autori presenti")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(path = "/autori")
	public ResponseEntity <List<Autore>> findAllAutori() {
		List<Autore> findAll = autoreService.findAllAutori();
		if(!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@Operation(summary = "Permette di cercare un autore tramite il suo id")
	@GetMapping(path = "/autore/{id}")
	public ResponseEntity<Autore> findByIdAutore(@PathVariable Long id) {
		Optional<Autore> findAutore = autoreService.findbyIdAutore(id);
		return new ResponseEntity<>(findAutore.get(), HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Permette di salvare un nuovo autore")
	@PostMapping(path = "/autore")
	public ResponseEntity<Autore> save(@RequestBody Autore autore) {
		Autore save = autoreService.save(autore);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

	// metodo per modificare un autore, bisogna passargli l'id dell'autore da modificare
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Permette di modificare un autore passandogli l'id")
	@PutMapping(path = "/autore/{id}")
	public ResponseEntity<Autore> update(@PathVariable Long id, @RequestBody Autore autore) {
		Autore save = autoreService.update(id, autore);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Permette di cancellare un autore tramite l'id")
	@DeleteMapping(path = "/autore/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Optional<Autore> find = autoreService.findbyIdAutore(id);
		if(!find.isEmpty()) {
			Autore delete = find.get();
			List<Libro> allLibri = libroService.findAllLibri();
			for(Libro libro : allLibri) {
				libro.deleteAllFromSet(delete);
			}
			libroService.deleteLibriNoAutori();
			autoreService.delete(id);
			return new ResponseEntity<>("Autore e libri corrispondenti cancellati", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Autore non trovato", HttpStatus.BAD_REQUEST);
		}
		
	}

}
