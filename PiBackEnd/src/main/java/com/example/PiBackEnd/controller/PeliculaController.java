package com.example.PiBackEnd.controller;

import com.example.PiBackEnd.domain.Pelicula;
import com.example.PiBackEnd.exceptions.ResourceBadRequestException;
import com.example.PiBackEnd.exceptions.ResourceNotFoundException;
import com.example.PiBackEnd.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @PostMapping
    public ResponseEntity<Pelicula> guardarOdontologo(@RequestBody Pelicula pelicula) throws ResourceBadRequestException {
        return ResponseEntity.ok(peliculaService.guardarPelicula(pelicula));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(peliculaService.buscarPelicula(id).get());
    }

    /*@PutMapping("/{peliculaId}/categorias/{categoriaId}")
    public ResponseEntity<Pelicula> agregarCategoriaAPelicula(@PathVariable Long peliculaId, @PathVariable Long categoriaId){
        return ResponseEntity.ok(peliculaService.agregarCategoriaAPelicula(peliculaId,categoriaId));
    }*/

    @PutMapping("/{peliculaId}/categorias/{categoria}")
    public ResponseEntity<Pelicula> agregarCategoriaAPeliculas(@PathVariable Long peliculaId, @PathVariable String categoria){
        return ResponseEntity.ok(peliculaService.agregarCategoriaAPeliculas(peliculaId,categoria));
    }
}
