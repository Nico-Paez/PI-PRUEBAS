package com.example.PiBackEnd.service;

import com.example.PiBackEnd.domain.Categoria;
import com.example.PiBackEnd.domain.Pelicula;
import com.example.PiBackEnd.repository.CategoriaRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoriaService {

    private final static Logger logger = Logger.getLogger(CategoriaService.class);
    private CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Set<Categoria> guardarCategorias(Set<Categoria> categorias) {
        logger.info("Guardando Categoria nueva");
        Set<Categoria> nuevasCategorias = new HashSet<>();
        for (Categoria categoria : categorias) {
            //Set<Pelicula> peliculasGuardadas = categoria.getPeliculas();
            Optional<Categoria> categoriaExistente = categoriaRepository.findByCategoria(categoria.getCategoria());
            if (categoriaExistente.isPresent()) {
                nuevasCategorias.add(categoriaExistente.get());
            } else {
                categoriaRepository.save(categoria);
                nuevasCategorias.add(categoria);
            }
            /*if(!peliculasGuardadas.contains(pelicula)){
                peliculasGuardadas.add(pelicula);
                categoria.setPeliculas(peliculasGuardadas);
            }*/
        }
        return nuevasCategorias;
    }

    /*public Categoria guardarCategorias(Categoria categoria) throws ResourceBadRequestException {
        //logger.info("Guardando Pelicula nueva");
        if(categoria == null){
            throw new ResourceBadRequestException("Error. La Pelicula tiene que contener todos sus campos");
        }else{
            return categoriaRepository.save(categoria);
        }
    }*/
}
