package com.example.DrawAwesome2.controllers;


import org.springframework.web.bind.annotation.RestController;
import com.example.DrawAwesome2.repositories.UserRepository;

@RestController
public class UserController {
	
	UserRepository repositorio;
	
	
	public UserController(UserRepository repositorio) {
		this.repositorio = repositorio;
	}
	/*
	
	@CrossOrigin("http://127.0.0.1:5500")
	@GetMapping("/api/peliculas")
	public List<Users> obtenerPeliculas(){
		return repositorio.findAll();
	}

	@CrossOrigin("http://127.0.0.1:5500")
	@GetMapping("/api/pelicula/{id}")
	public ResponseEntity<Users> obtenerPelicula(@PathVariable Long id){
	}
	
	@CrossOrigin("http://127.0.0.1:5500")
	@PostMapping("/api/peliculas")
	public ResponseEntity<Peliculas> guardarPelicula(@RequestBody Peliculas peli){
	}
	
	
	
	@CrossOrigin("http://127.0.0.1:5500")
	@PutMapping("/api/peliculas")
	public ResponseEntity<Peliculas> actualizarPelicula(@RequestBody Peliculas peli){
	}
	
	
	
	@CrossOrigin("http://127.0.0.1:5500")
	@DeleteMapping("/api/pelicula/{id}")
	public ResponseEntity<Peliculas> borrarPelicula(@PathVariable Long id){
	}*/
	
}
