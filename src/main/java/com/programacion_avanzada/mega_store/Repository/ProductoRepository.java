package com.programacion_avanzada.mega_store.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.programacion_avanzada.mega_store.Modelos.Producto;

public interface ProductoRepository  extends JpaRepository<Producto, Long>{
    //Metodo para corroborar que no existen 2 nombres iguales.
    boolean existsByNombre(String nombre);

    //Metodo para buscar productos que esten solamente activos.
    List<Producto> findAllByEstaActivoIsTrue();
}
