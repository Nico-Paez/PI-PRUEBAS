package com.example.PiBackEnd.service;

/*import com.example.PiBackEnd.domain.Fecha;
import com.example.PiBackEnd.domain.Pelicula;
import com.example.PiBackEnd.exceptions.ResourceNotFoundException;
import com.example.PiBackEnd.repository.FechaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
public class FechaService {

    private FechaRepository fechaRepository;

    @Autowired
    public FechaService(FechaRepository fechaRepository) {
        this.fechaRepository = fechaRepository;
    }

    public Optional<Fecha> buscarFecha(Long id) throws ResourceNotFoundException {
        Optional<Fecha> fechaBuscada = fechaRepository.findById(id);
        if(fechaBuscada.isPresent()){
            Set<Pelicula> peliculas = fechaBuscada.get().getPeliculas();
            fechaBuscada.get().setPeliculas(peliculas);
            return fechaBuscada;
        } else{
            throw new ResourceNotFoundException("Error. No existe la Fecha con id = " + id + ".");
        }
    }
}*/
