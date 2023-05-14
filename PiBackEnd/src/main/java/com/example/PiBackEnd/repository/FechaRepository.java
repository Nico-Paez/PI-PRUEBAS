package com.example.PiBackEnd.repository;

import com.example.PiBackEnd.domain.Fecha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface FechaRepository extends JpaRepository<Fecha,Long> {
    Optional<Fecha> findByFecha(LocalDate fecha);
}
