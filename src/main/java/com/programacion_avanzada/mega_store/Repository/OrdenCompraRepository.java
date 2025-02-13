package com.programacion_avanzada.mega_store.Repository;

import com.programacion_avanzada.mega_store.Modelos.OrdenCompra;
import com.programacion_avanzada.mega_store.Modelos.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
    List<OrdenCompra> findByUsuario(Usuario usuario);

    OrdenCompra findById(long id);
    
 }
