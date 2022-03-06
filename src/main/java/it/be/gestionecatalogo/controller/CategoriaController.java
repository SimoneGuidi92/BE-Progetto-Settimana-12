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
import it.be.gestionecatalogo.model.Categoria;
import it.be.gestionecatalogo.model.Libro;
import it.be.gestionecatalogo.service.CategoriaService;
import it.be.gestionecatalogo.service.LibroService;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api")
public class CategoriaController {

	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private LibroService libroService;

	// PER POTER ACCEDERE ALLA PAGINA SWAGGER BISOGNA INSERIRE QUESTO URL: localhost:8080/swagger-ui.html

	
	@Operation(summary = "Mostra la lista di tutte le categorie presenti")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(path = "/categorie")
	public ResponseEntity <List<Categoria>> findAllCategorie() {
		List<Categoria> findAll = categoriaService.findAllCategorie();
		if(!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@Operation(summary = "Permette di cercare una categoria tramite il suo id")
	@GetMapping(path = "/categoria/{id}")
	public ResponseEntity<Categoria> findByIdCategoria(@PathVariable Long id) {
		Optional<Categoria> findCategoria = categoriaService.findbyIdCategoria(id);
		return new ResponseEntity<>(findCategoria.get(), HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Permette di salvare una nuova categoria")
	@PostMapping(path = "/categoria")
	public ResponseEntity<Categoria> save(@RequestBody Categoria categoria) {
		Categoria save = categoriaService.save(categoria);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

	// metodo per modificare una categoria, bisogna passargli l'id della categoria da modificare
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Permette di modificare una categoria passandogli l'id")
	@PutMapping(path = "/categoria/{id}")
	public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoria) {
		Categoria save = categoriaService.update(id, categoria);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Permette di cancellare un categoria tramite l'id")
	@DeleteMapping(path = "/categoria/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Optional<Categoria> find = categoriaService.findbyIdCategoria(id);
		if(!find.isEmpty()) {
			Categoria delete = find.get();
			List<Libro> allLibri = libroService.findAllLibri();
			for(Libro libro : allLibri) {
				libro.deleteAllCategorieFromSet(delete);
			}
			categoriaService.delete(id);
			return new ResponseEntity<>("Categoria cancellata", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Categoria non trovata", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
}
