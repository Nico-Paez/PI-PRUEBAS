package com.example.PiBackEnd.repository;

import com.example.PiBackEnd.domain.Pelicula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula,Long> {
    Optional<Pelicula> findByTitulo(String titulo);
    Optional<Pelicula> findByTituloAndVigente(String titulo, Boolean vigente);
    Page<Pelicula> findAll(Pageable pageable);
    List<Pelicula> findAllByVigenteTrue();
    Page<Pelicula> findAllByVigenteTrue(Pageable pageable);
    Optional<Pelicula> findByIdAndVigente(Long id, Boolean vigente);
}
