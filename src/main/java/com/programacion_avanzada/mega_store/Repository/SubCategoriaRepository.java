package com.programacion_avanzada.mega_store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programacion_avanzada.mega_store.Modelos.SubCategoria;

public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Long>{
    
    //Metodo para corroborar que no existen 2 metodos iguales.
    boolean existsByNombre(String nombre);  
}
