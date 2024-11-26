package com.programacion_avanzada.mega_store.Service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.programacion_avanzada.mega_store.DTOs.InicioSesionDTO;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;

public class SesionService implements ISesionService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public boolean iniciarSesion(InicioSesionDTO inicioSesion) {
       Usuario usuario = usuarioRepository.findByEmail(inicioSesion.getEmail());
       validarEmail(inicioSesion.getEmail());
       if(usuario == null || !usuario.getContrasena().equals(inicioSesion.getContrasena())) {
           return false;    
       }
       


        return true;
    }

    private void validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío.");
        }
        if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email)) {
            throw new IllegalArgumentException("El email no es válido.");
        }
    }


    
}
