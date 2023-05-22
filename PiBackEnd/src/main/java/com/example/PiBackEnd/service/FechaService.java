package com.example.PiBackEnd.service;

import com.example.PiBackEnd.domain.Fecha;
import com.example.PiBackEnd.domain.Pelicula;
import com.example.PiBackEnd.exceptions.ResourceBadRequestException;
import com.example.PiBackEnd.exceptions.ResourceNotFoundException;
import com.example.PiBackEnd.repository.FechaRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class FechaService {

    private final static Logger logger = Logger.getLogger(FechaService.class);
    private FechaRepository fechaRepository;

    @Autowired
    public FechaService(FechaRepository fechaRepository) {
        this.fechaRepository = fechaRepository;
    }

    public Set<Fecha> guardarFechas(Set<Fecha> fechas) throws ResourceBadRequestException {
        logger.info("Guardando Fecha nueva");
        Set<Fecha> nuevasFechas = new HashSet<>();
        LocalDate fechaHoy = LocalDate.now();
        int fechasChequeadas = 0;
        for (Fecha fechaChequear : fechas) {
            if (fechaChequear.getFecha().isBefore(fechaHoy)) {
                fechasChequeadas++;
            }
        }
        if (fechasChequeadas > 0) {
            throw new ResourceBadRequestException("Error. Las Fechas deben ser posteriores a " + fechaHoy);
        } else {
            for (Fecha fecha : fechas) {
                //Set<Pelicula> peliculasGuardadas = fecha.getPeliculas();
                Optional<Fecha> fechaExistente = fechaRepository.findByFecha(fecha.getFecha());
                if (fechaExistente.isPresent()) {
                    nuevasFechas.add(fechaExistente.get());
                } else {
                    fechaRepository.save(fecha);
                    nuevasFechas.add(fecha);
                }
                /*if(!peliculasGuardadas.contains(pelicula)){
                    peliculasGuardadas.add(pelicula);
                    fecha.setPeliculas(peliculasGuardadas);
                }*/
            }
            return nuevasFechas;
        }
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
}
