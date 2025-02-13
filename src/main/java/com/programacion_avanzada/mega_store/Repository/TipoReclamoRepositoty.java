package com.programacion_avanzada.mega_store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programacion_avanzada.mega_store.Modelos.TipoReclamo;

public interface TipoReclamoRepositoty extends JpaRepository<TipoReclamo, Long> {
    TipoReclamo findById(long id);
}
