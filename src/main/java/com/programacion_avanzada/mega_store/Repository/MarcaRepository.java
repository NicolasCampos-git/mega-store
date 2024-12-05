package com.programacion_avanzada.mega_store.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.programacion_avanzada.mega_store.Modelos.Marca;



public interface MarcaRepository extends JpaRepository<Marca, Long>{

    //Metodo para corroborar que no existen 2 nombres iguales.
    boolean existsByNombre(String nombre);
    
    List<Marca> findAllByEstaActivoIsTrue();

    //Metodo para buscar categorias por nombre.
    Optional<Marca> findByNombre(String nombre);

}
