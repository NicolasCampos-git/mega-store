

package com.programacion_avanzada.mega_store.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.programacion_avanzada.mega_store.Modelos.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    Usuario findByEmail(String email);
}