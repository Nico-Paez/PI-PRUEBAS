package com.example.PiBackEnd.service;

import com.example.PiBackEnd.domain.Categoria;
import com.example.PiBackEnd.domain.Fecha;
import com.example.PiBackEnd.domain.Pelicula;
import com.example.PiBackEnd.exceptions.ResourceBadRequestException;
import com.example.PiBackEnd.exceptions.ResourceNoContentException;
import com.example.PiBackEnd.exceptions.ResourceNotFoundException;
import com.example.PiBackEnd.repository.CategoriaRepository;
import com.example.PiBackEnd.repository.FechaRepository;
import com.example.PiBackEnd.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import java.time.LocalDate;
import java.util.*;

@Service
public class PeliculaService {

    private final static Logger logger = Logger.getLogger(PeliculaService.class);
    private PeliculaRepository peliculaRepository;
    private CategoriaRepository categoriaRepository;
    private FechaRepository fechaRepository;

    @Autowired
    public PeliculaService(PeliculaRepository peliculaRepository, CategoriaRepository categoriaRepository, FechaRepository fechaRepository) {
        this.peliculaRepository = peliculaRepository;
        this.categoriaRepository = categoriaRepository;
        this.fechaRepository = fechaRepository;
    }

    public Pelicula guardarPelicula(Pelicula pelicula) throws ResourceBadRequestException {
        logger.info("Guardando Pelicula nueva");
        if (pelicula.chequearAtributosVacios()) {
            throw new ResourceBadRequestException("Error. La Pelicula tiene que contener todos sus campos");
        } else {
            String titulo = pelicula.getTitulo();
            if (peliculaRepository.findByTitulo(titulo).isPresent()) {
                throw new ResourceBadRequestException("Error. Ya existe una Pelicula con el mismo titulo");
            } else {
                logger.info("Guardando Categorias");
                Set<Categoria> categorias = pelicula.getCategorias();
                Set<Categoria> nuevasCategorias = new HashSet<>();
                for (Categoria categoria : categorias) {
                    Set<Pelicula> peliculasGuardadas = categoria.getPeliculas();
                    Optional<Categoria> categoriaExistente = categoriaRepository.findByCategoria(categoria.getCategoria());
                    if (categoriaExistente.isPresent()) {
                        nuevasCategorias.add(categoriaExistente.get());
                    } else {
                        categoriaRepository.save(categoria);
                        nuevasCategorias.add(categoria);
                    }
                    if(!peliculasGuardadas.contains(pelicula)){
                        peliculasGuardadas.add(pelicula);
                        categoria.setPeliculas(peliculasGuardadas);
                    }
                }
                logger.info("Guardando Fechas");
                Set<Fecha> fechas = pelicula.getFechas();
                Set<Fecha> nuevasFechas = new HashSet<>();
                    for (Fecha fecha : fechas) {
                        Set<Pelicula> peliculasGuardadas = fecha.getPeliculas();
                        Optional<Fecha> fechaExistente = fechaRepository.findByFecha(fecha.getFecha());
                        if (fechaExistente.isPresent()) {
                            nuevasFechas.add(fechaExistente.get());
                        } else {
                            fechaRepository.save(fecha);
                            nuevasFechas.add(fecha);
                        }
                        if(!peliculasGuardadas.contains(pelicula)){
                            peliculasGuardadas.add(pelicula);
                            fecha.setPeliculas(peliculasGuardadas);
                        }
                    }
                pelicula.setCategorias(nuevasCategorias);
                pelicula.setFechas(nuevasFechas);
                return peliculaRepository.save(pelicula);
            }
        }
    }


    public Pelicula actualizarPelicula(Pelicula pelicula) throws ResourceNotFoundException, ResourceBadRequestException {
        logger.info("Actualizando Pelicula con id " + pelicula.getId());
        Optional<Pelicula> peliculaBuscada = peliculaRepository.findById(pelicula.getId());
        if (peliculaBuscada.isEmpty()) {
            throw new ResourceNotFoundException("Error. Pelicula con id " + pelicula.getId() + " no encontrada");
        }else{
            if (pelicula.chequearAtributosVacios()) {
                throw new ResourceBadRequestException("Error. La Pelicula tiene que contener todos sus campos");
            }else{
                if (pelicula.getTitulo() != null) {
                    String titulo = pelicula.getTitulo();
                    if (peliculaRepository.findByTitulo(titulo).isPresent() && !titulo.equals(pelicula.getTitulo())) {
                        throw new ResourceBadRequestException("Error. Ya existe una Pelicula con el mismo titulo");
                    }
                    pelicula.setTitulo(titulo);
                }
                if (pelicula.getImagen() != null) {
                    pelicula.setImagen(pelicula.getImagen());
                }
                if (pelicula.getDescripcion() != null) {
                    pelicula.setDescripcion(pelicula.getDescripcion());
                }
                logger.info("Guardando Categorias");
                Set<Categoria> categorias = pelicula.getCategorias();
                Set<Categoria> nuevasCategorias = new HashSet<>();
                for (Categoria categoria : categorias) {
                    Set<Pelicula> peliculasGuardadas = categoria.getPeliculas();
                    Optional<Categoria> categoriaExistente = categoriaRepository.findByCategoria(categoria.getCategoria());
                    if (categoriaExistente.isPresent()) {
                        nuevasCategorias.add(categoriaExistente.get());
                    } else {
                        categoriaRepository.save(categoria);
                        nuevasCategorias.add(categoria);
                    }
                    if(!peliculasGuardadas.contains(pelicula)){
                        peliculasGuardadas.add(pelicula);
                        categoria.setPeliculas(peliculasGuardadas);
                    }
                }
                logger.info("Guardando Fechas");
                Set<Fecha> fechas = pelicula.getFechas();
                Set<Fecha> nuevasFechas = new HashSet<>();
                for (Fecha fecha : fechas) {
                    Set<Pelicula> peliculasGuardadas = fecha.getPeliculas();
                    Optional<Fecha> fechaExistente = fechaRepository.findByFecha(fecha.getFecha());
                    if (fechaExistente.isPresent()) {
                        nuevasFechas.add(fechaExistente.get());
                    } else {
                        fechaRepository.save(fecha);
                        nuevasFechas.add(fecha);
                    }
                    if(!peliculasGuardadas.contains(pelicula)){
                        peliculasGuardadas.add(pelicula);
                        fecha.setPeliculas(peliculasGuardadas);
                    }
                }
                pelicula.setCategorias(nuevasCategorias);
                pelicula.setFechas(nuevasFechas);
                return peliculaRepository.save(pelicula);
            }
        }

    }

    public void eliminarPelicula(Long id) throws ResourceNotFoundException {
        logger.warn("Borrando Pelicula con id = " + id);
        Optional<Pelicula> peliculaBuscada = peliculaRepository.findById(id);
        if(peliculaBuscada.isPresent()){
            peliculaRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Error. No existe la Pelicula con id = " + id + ".");
        }
    }

    public Optional<Pelicula> buscarPeliculaPorTitulo(String titulo) throws ResourceNotFoundException {
        logger.info("Buscando Pelicula con titulo: " + titulo);
        Optional<Pelicula> peliculaBuscada = peliculaRepository.findByTitulo(titulo);
        if (peliculaBuscada.isPresent()){
            return peliculaBuscada;
        }
        else{
            throw new ResourceNotFoundException("Error. No existe la Pelicula con titulo: " + titulo + ".");
        }
    }

    public Optional<Pelicula> buscarPelicula(Long id) throws ResourceNotFoundException {
        logger.info("Buscando Pelicula con id = " + id);
        Optional<Pelicula> peliculaBuscada = peliculaRepository.findById(id);
        if (peliculaBuscada.isPresent()){
            return peliculaBuscada;
        }
        else{
            throw new ResourceNotFoundException("Error. No existe la Pelicula con id = " + id + ".");
        }
    }

    public List<Pelicula> buscarPeliculasPorFecha(LocalDate fecha) throws ResourceNoContentException {
        logger.info("Buscando todas las Peliculas por fecha");
        List<Pelicula> todasLasPeliculas = peliculaRepository.findAll();
        List<Pelicula> peliculasEncontradas = new ArrayList<>();
        for (Pelicula pelicula : todasLasPeliculas) {
            for (Fecha fechaPelicula : pelicula.getFechas()) {
                if (fechaPelicula.getFecha().equals(fecha)) {
                    peliculasEncontradas.add(pelicula);
                    break;
                }
            }
        }
        if(peliculasEncontradas.size() > 0){
            return peliculasEncontradas;
        }else{
            throw new ResourceNoContentException("Error. No existen Peliculas registradas con categoria: " + fecha + ".");
        }
    }

    public List<Pelicula> buscarPeliculasPorCategoria(String categoria) throws ResourceNoContentException {
        logger.info("Buscando todas las Peliculas por categoria");
        List<Pelicula> todasLasPeliculas = peliculaRepository.findAll();
        List<Pelicula> peliculasEncontradas = new ArrayList<>();
        for (Pelicula pelicula : todasLasPeliculas) {
            for (Categoria categoriaPelicula : pelicula.getCategorias()) {
                if (categoriaPelicula.getCategoria().equals(categoria)) {
                    peliculasEncontradas.add(pelicula);
                    break;
                }
            }
        }
        if(peliculasEncontradas.size() > 0){
            return peliculasEncontradas;
        }else{
            throw new ResourceNoContentException("Error. No existen Peliculas registradas con categoria: " + categoria + ".");
        }
    }

    public List<Pelicula> buscarTodasPeliculas() throws ResourceNoContentException {
        logger.info("Buscando todas las Peliculas");
        List<Pelicula> lista = peliculaRepository.findAll();
        if(lista.size() > 0){
            return lista;
        }else{
            throw new ResourceNoContentException("Error. No existen Peliculas registradas.");
        }
    }
}
