

package com.programacion_avanzada.mega_store.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.programacion_avanzada.mega_store.Modelos.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    //Metodo para comprobar que el mail no este registrado previamente.
    boolean existsByEmail(String email);

    Usuario findByEmail(String email);

    
}