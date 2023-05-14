package com.example.PiBackEnd.controller;

import com.example.PiBackEnd.domain.Pelicula;
import com.example.PiBackEnd.exceptions.ResourceBadRequestException;
import com.example.PiBackEnd.exceptions.ResourceNoContentException;
import com.example.PiBackEnd.exceptions.ResourceNotFoundException;
import com.example.PiBackEnd.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPelicula(@PathVariable Long id) throws ResourceNotFoundException {
        peliculaService.eliminarPelicula(id);
        return ResponseEntity.ok("Eliminación de la Pelicula con id = " + id + " con éxito");
    }

    @PostMapping
    public ResponseEntity<Pelicula> guardarPelicula(@RequestBody Pelicula pelicula) throws ResourceBadRequestException {
        return ResponseEntity.ok(peliculaService.guardarPelicula(pelicula));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> buscarPelicula(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(peliculaService.buscarPelicula(id).get());
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Pelicula> buscarPeliculaPorTitulo(@PathVariable String titulo) throws ResourceNotFoundException {
        return ResponseEntity.ok(peliculaService.buscarPeliculaPorTitulo(titulo).get());
    }

    @GetMapping
    public ResponseEntity<List<Pelicula>> buscarTodasPeliculas() throws ResourceNoContentException {
        return ResponseEntity.ok(peliculaService.buscarTodasPeliculas());
    }

    @GetMapping("/fecha/{fechaString}")
    public ResponseEntity<List<Pelicula>> buscarPeliculasPorFecha(@PathVariable String fechaString) throws ResourceNoContentException {
        LocalDate fecha = LocalDate.parse(fechaString);
        return ResponseEntity.ok(peliculaService.buscarPeliculasPorFecha(fecha));
    }

    @PutMapping
    public ResponseEntity<Pelicula> actualizarPelicula(@RequestBody Pelicula pelicula) throws ResourceBadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(peliculaService.actualizarPelicula(pelicula));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Pelicula>> buscarPeliculasPorCategoria(@PathVariable String categoria) throws ResourceNoContentException {
        return ResponseEntity.ok(peliculaService.buscarPeliculasPorCategoria(categoria));
    }
}
