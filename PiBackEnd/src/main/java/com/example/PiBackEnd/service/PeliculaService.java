package com.example.PiBackEnd.service;

import com.example.PiBackEnd.domain.Categoria;
import com.example.PiBackEnd.domain.Pelicula;
import com.example.PiBackEnd.exceptions.ResourceBadRequestException;
import com.example.PiBackEnd.exceptions.ResourceNoContentException;
import com.example.PiBackEnd.exceptions.ResourceNotFoundException;
import com.example.PiBackEnd.repository.CategoriaRepository;
import com.example.PiBackEnd.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PeliculaService {

    private PeliculaRepository peliculaRepository;
    private CategoriaRepository categoriaRepository;

    @Autowired
    public PeliculaService(PeliculaRepository peliculaRepository, CategoriaRepository categoriaRepository) {
        this.peliculaRepository = peliculaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /*public Pelicula guardarPelicula(Pelicula pelicula) throws ResourceBadRequestException {
        //logger.info("Guardando Pelicula nueva");
        if (pelicula.chequearAtributosVacios()) {
            throw new ResourceBadRequestException("Error. La Pelicula tiene que contener todos sus campos");
        } else {
            String titulo = pelicula.getTitulo();
            if (peliculaRepository.findByTitulo(titulo).isPresent()) {
                throw new ResourceBadRequestException("Error. Ya existe una Pelicula con el mismo titulo");
            } else {
                return peliculaRepository.save(pelicula);
            }
        }
    }*/

    public Pelicula guardarPelicula(Pelicula pelicula) throws ResourceBadRequestException {
        //logger.info("Guardando Pelicula nueva");
        if (pelicula.chequearAtributosVacios()) {
            throw new ResourceBadRequestException("Error. La Pelicula tiene que contener todos sus campos");
        } else {
            String titulo = pelicula.getTitulo();
            if (peliculaRepository.findByTitulo(titulo).isPresent()) {
                throw new ResourceBadRequestException("Error. Ya existe una Pelicula con el mismo titulo");
            } else {
                Set<Categoria> categorias = pelicula.getCategorias();
                Set<Categoria> nuevasCategorias = new HashSet<>();
                for (Categoria categoria : categorias) {
                    Optional<Categoria> categoriaExistente = categoriaRepository.findByCategoria(categoria.getCategoria());
                    if (categoriaExistente.isPresent()) {
                        //creo falta agregarle la pelicula a la categoria (para dsp poder usar peliculas desde categorias)
                        //fecha.setPelicula(peliculaGuardada);
                        nuevasCategorias.add(categoriaExistente.get());
                    } else {
                        //aca tambien
                        categoriaRepository.save(categoria);
                        nuevasCategorias.add(categoria);
                    }
                }
                //o aca
                pelicula.setCategorias(nuevasCategorias);
                return peliculaRepository.save(pelicula);
            }
        }
    }

    /*public Pelicula guardarPelicula(Pelicula pelicula) throws ResourceBadRequestException {
        if (pelicula.chequearAtributosVacios()) {
            throw new ResourceBadRequestException("Error. La Pelicula tiene que contener todos sus campos");
        } else {
            String titulo = pelicula.getTitulo();
            if (peliculaRepository.findByTitulo(titulo).isPresent()) {
                throw new ResourceBadRequestException("Error. Ya existe una Pelicula con el mismo titulo");
            } else {
                Set<Categoria> categorias = pelicula.getCategorias();
                Set<Categoria> nuevasCategorias = new HashSet<>();
                for (Categoria categoria : categorias) {
                    Optional<Categoria> categoriaExistente = categoriaRepository.findByCategoria(categoria.getCategoria());
                    if (categoriaExistente.isPresent()) {
                        categoria.setId(categoriaExistente.get().getId());
                    } else {
                        nuevasCategorias.add(categoria);
                    }
                }
                categorias.addAll(nuevasCategorias);
                pelicula.setCategorias(categorias);
                return peliculaRepository.save(pelicula);
            }
        }
    }*/

    public Optional<Pelicula> buscarPelicula(Long id) throws ResourceNotFoundException {
        Optional<Pelicula> peliculaBuscada = peliculaRepository.findById(id);
        if (peliculaBuscada.isPresent()){
            Pelicula pelicula = peliculaBuscada.get();
            List<Categoria> categorias = new ArrayList<>(pelicula.getCategorias());
            categorias.sort(Comparator.comparing(Categoria::getId));
            pelicula.setCategorias(new HashSet<>(categorias));
            return peliculaBuscada;
        }
        else{
            throw new ResourceNotFoundException("Error. No existe la Pelicula con id = " + id + ".");
        }
    }

    /*public Optional<Pelicula> buscarPelicula(Long id) throws ResourceNotFoundException {
        //logger.info("Buscando Pelicula con id = " + id);
        Optional<Pelicula> peliculaBuscada = peliculaRepository.findById(id);
        if (peliculaBuscada.isPresent()){
            Pelicula pelicula = peliculaBuscada.get();
            Set<Categoria> categorias = pelicula.getCategorias();
            pelicula.setCategorias(categorias);
            return peliculaBuscada;
        }
        else{
            throw new ResourceNotFoundException("Error. No existe la Pelicula con id = " + id + ".");
        }
    }*/

    /*public Pelicula agregarCategoriaAPelicula(Long peliculaId, Long categoriaId) {
        Pelicula pelicula = peliculaRepository.findById(peliculaId).get();
        Categoria categoria = categoriaRepository.findById(categoriaId).get();
        pelicula.agregarCategoria(categoria);
        return peliculaRepository.save(pelicula);
    }*/

    public List<Pelicula> buscarTodasPeliculas() throws ResourceNoContentException {
        //logger.info("Buscando todos las Peliculas");
        List<Pelicula> lista = peliculaRepository.findAll();
        if(lista.size() > 0){
            return lista;
        }else{
            throw new ResourceNoContentException("Error. No existen Peliculas registradas.");
        }
    }

    public Pelicula agregarCategoriaAPeliculas(Long peliculaId, String categoria) {
        Pelicula pelicula = peliculaRepository.findById(peliculaId).get();
        Categoria categoria1 = categoriaRepository.findByCategoria(categoria).get();
        pelicula.agregarCategoria(categoria1);
        return peliculaRepository.save(pelicula);
    }
}
