package com.programacion_avanzada.mega_store.Repository;

import com.programacion_avanzada.mega_store.Modelos.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Optional<Estado> findByNombre(String nombre);
}
