package com.example.PiBackEnd.repository;

import com.example.PiBackEnd.domain.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula,Long> {
    Optional<Object> findByTitulo(String titulo);
}
