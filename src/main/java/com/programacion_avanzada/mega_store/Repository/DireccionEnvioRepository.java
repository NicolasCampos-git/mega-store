package com.programacion_avanzada.mega_store.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programacion_avanzada.mega_store.Modelos.DireccionEnvio;

public interface DireccionEnvioRepository extends JpaRepository<DireccionEnvio, Long>{
    List<DireccionEnvio> findByUsuarioId(Long usuarioId);
}
