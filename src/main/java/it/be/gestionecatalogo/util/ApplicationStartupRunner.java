package it.be.gestionecatalogo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.be.gestionecatalogo.model.Autore;
import it.be.gestionecatalogo.model.Categoria;
import it.be.gestionecatalogo.model.Libro;
import it.be.gestionecatalogo.repository.AutoreRepository;
import it.be.gestionecatalogo.repository.CategoriaRepository;
import it.be.gestionecatalogo.repository.LibroRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe utility per l'inizializzazione del database all'avvio
 * dell'applicazione
 *
 */
@Component
@Slf4j
public class ApplicationStartupRunner implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutoreRepository autoreRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;


	@Override
	public void run(String... args) throws Exception {

		Autore a1 = new Autore();
		a1.setNome("Jack");
		a1.setCognome("Sparrow");
		autoreRepository.save(a1);
		
		Autore a2 = new Autore();
		a2.setNome("Bianca");
		a2.setCognome("Verdi");
		autoreRepository.save(a2);
		
		Autore a3 = new Autore();
		a3.setNome("Mario");
		a3.setCognome("Rossi");
		autoreRepository.save(a3);
		
		
		Categoria c1 = new Categoria();
		c1.setNome("Horror");
		categoriaRepository.save(c1);
		Categoria c2 = new Categoria();
		c2.setNome("Fantasy");
		categoriaRepository.save(c2);
		
		
		Libro l1 = new Libro();
		l1.setTitolo("Libro 1");
		l1.setAnnoPubblicazione(2022);
		l1.setPrezzo(12.50);
		l1.getAutori().add(a1);
		l1.getCategorie().add(c1);
		libroRepository.save(l1);
		
		Libro l2 = new Libro();
		l2.setTitolo("Libro 2 il ritorno");
		l2.setAnnoPubblicazione(2019);
		l2.setPrezzo(29.90);
		l2.getAutori().add(a2);
		l2.getCategorie().add(c2);
		libroRepository.save(l2);
		
		Libro l3 = new Libro();
		l3.setTitolo("Il libro perduto");
		l3.setAnnoPubblicazione(2000);
		l3.setPrezzo(49.90);
		l3.getAutori().add(a2);
		l3.getCategorie().add(c2);
		libroRepository.save(l3);
		
		
		
		
		
		
		
		
//		private void initUtente() {
//
//			Utente utente = new Utente();
//			utente.setEmail("user@email.em");
//			utente.setNome("Mario Rossi");
//			utente.setUserName("m.rossi");
//			utente.setPassword("test");
//			utenteRepository.save(utente);
//			log.info("Saved Utente: {}", utente.getNome());
//
//		}
//
//		private Citta initCitta() {
//
//			Citta citta = new Citta();
//			citta.setNome("Roma");
//			cittaRepository.save(citta);
//			log.info("Saved Città: {}", citta.getNome());
//			return citta;
//
//		}
		

//		Citta citta = initCitta();
//
//		List<Edificio> edifici = initEdificio(citta);
//
//		initPostazione(edifici);
//
//		initUtente();
//
//		initPrenotazione();
	}
	
	
	
	
}
/*
	/**
	 * Inserisce le postazioni per ciascun edificio
	 * 
	 * @param edifici lista di edifici da popolare con le postazioni
	 */
	/*
	private void initPostazione(List<Edificio> edifici) {

		for (Edificio edificio : edifici) {

			for (int i = 1; i < 4; i++) {
				Postazione postazione = new Postazione();
				postazione.setCodice("P_" + i + "_" + edificio.getNome());
				postazione.setDescrizione("Postazione " + postazione.getCodice());
				postazione.setEdificio(edificio);
				postazione.setNumeroMassimoOccupanti(2);
				postazione.setTipo(TipoPostazione.PRIVATO);
				postazioneRepository.save(postazione);
				log.info("Saved Postazione: {}", postazione.getCodice());

			}

		}

	}

	/**
	 * Genera la lista di edifici di una città
	 * 
	 * @param citta in cui sono ubicati gli edifici
	 * @return lista degli edifici
	 */
	/*
	private List<Edificio> initEdificio(Citta citta) {

		List<Edificio> edifici = new ArrayList<>();

		Edificio edificio = new Edificio();
		edificio.setCitta(citta);
		edificio.setIndirizzo("Via Nazionale, 6");
		edificio.setNome("Sede Centrale - Roma");
		edificioRepository.save(edificio);
		edifici.add(edificio);
		log.info("Saved Edificio: {}", edificio.getNome());

		edificio = new Edificio();
		edificio.setCitta(citta);
		edificio.setIndirizzo("Via Ponzio, 1");
		edificio.setNome("Sede Distaccata - Roma");
		edificioRepository.save(edificio);
		edifici.add(edificio);
		log.info("Saved Edificio: {}", edificio.getNome());

		return edifici;

	}

	/**
	 * Inserisce un utente
	 */
	/*
	private void initUtente() {

		Utente utente = new Utente();
		utente.setEmail("user@email.em");
		utente.setNome("Mario Rossi");
		utente.setUserName("m.rossi");
		utente.setPassword("test");
		utenteRepository.save(utente);
		log.info("Saved Utente: {}", utente.getNome());

	}

	private Citta initCitta() {

		Citta citta = new Citta();
		citta.setNome("Roma");
		cittaRepository.save(citta);
		log.info("Saved Città: {}", citta.getNome());
		return citta;

	}

	/**
	 * Inserisce una prenotazione di test recuperando l'unica città inserita e la
	 * prima postazione (id=1) e settando la data prenotata per il giorno successivo
	 * a quello dell'operazione
	 * 
	 */
	/*
	private void initPrenotazione() {
		Postazione postazione = postazioneRepository.getById(1L);
		Utente utente = utenteRepository.getById(1);
		Prenotazione prenotazione = new Prenotazione();
		prenotazione.setUtente(utente);
		prenotazione.setPostazione(postazione);
		prenotazione.setDataPrenotata(LocalDate.now().plusDays(2));
		prenotazione.setDataPrenotazione(LocalDate.now());
		
		prenotazioneRepository.save(prenotazione);
	}

}*/
