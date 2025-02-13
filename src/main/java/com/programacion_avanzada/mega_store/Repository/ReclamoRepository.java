package com.programacion_avanzada.mega_store.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programacion_avanzada.mega_store.Modelos.Reclamo;

public interface ReclamoRepository extends JpaRepository<Reclamo, Long>{
    boolean existsById(long id);

    List<Reclamo> findByUsuarioId(Long idUsuario);
}
