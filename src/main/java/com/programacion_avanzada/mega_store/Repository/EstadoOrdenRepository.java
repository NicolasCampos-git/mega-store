package com.programacion_avanzada.mega_store.Repository;

import com.programacion_avanzada.mega_store.Modelos.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, Long> {
    Optional<EstadoOrden> findByNombre(String nombre);
}
