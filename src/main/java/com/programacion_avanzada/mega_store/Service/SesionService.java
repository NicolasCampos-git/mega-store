package com.programacion_avanzada.mega_store.Service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.InicioSesionDTO;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;
import com.programacion_avanzada.mega_store.Service.Interfaces.ISesionService;

@Service
public class SesionService implements ISesionService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario iniciarSesion(InicioSesionDTO inicioSesion) {
       Usuario usuario = usuarioRepository.findByEmail(inicioSesion.getEmail());
       validarEmail(inicioSesion.getEmail());
       if(usuario == null || !usuario.getContrasena().equals(inicioSesion.getContrasena()) || !usuario.getEmail().equals(inicioSesion.getEmail())){
           throw new IllegalArgumentException("El email o la contraseña son incorrectos.");   
       }
       


        return usuario;
    }

    @Override
    public Usuario recuperarContrasena(InicioSesionDTO inicioSesion) {
        Usuario usuario = usuarioRepository.findByEmail(inicioSesion.getEmail());
        validarEmail(inicioSesion.getEmail());
        if(usuario == null){
            throw new IllegalArgumentException("El email no esta registrado.");
        }
        
        usuario.setContrasena("1234");
        usuario = usuarioRepository.save(usuario);
        return usuario;
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
