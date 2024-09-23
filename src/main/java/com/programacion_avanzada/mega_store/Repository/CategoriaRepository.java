package com.programacion_avanzada.mega_store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programacion_avanzada.mega_store.Modelos.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    boolean existsByNombre(String nombre);
}
