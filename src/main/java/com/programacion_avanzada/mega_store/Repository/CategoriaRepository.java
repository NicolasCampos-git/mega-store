package com.programacion_avanzada.mega_store.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programacion_avanzada.mega_store.Modelos.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

    //Metodo para corroborar que no existen 2 nombres iguales.
    boolean existsByNombre(String nombre);

    //Metodo para buscar categorias que esten solamente activos.
    List<Categoria> findAllByEstaActivoIsTrue();

    Optional<Categoria> findByNombre(String nombre);
}
