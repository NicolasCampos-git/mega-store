package com.programacion_avanzada.mega_store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programacion_avanzada.mega_store.Modelos.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long>{
    boolean existsByNombre(String nombre);
}
