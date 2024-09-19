package com.programacion_avanzada.mega_store.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;

import com.programacion_avanzada.mega_store.Mapper.RegistroUsuarioMapper;

import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.DireccionEnvioRepository;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    DireccionEnvioRepository direccionEnvioRepository;

    //Meotodo que registra al usuario en la web
    @Override
    public RegistroUsuarioDto registrarUsuario(RegistroUsuarioDto dto) {

        //Convierte el DTO a una tiendad con sus datos.
        Usuario usuario = RegistroUsuarioMapper.toEntity(dto);
        
        //Verifica que el correo no este registrado.
        if(verificarEmail(usuario.getEmail()) == true){
            return null;
        }

        //Por temas de practicidad agrega por defecto el rol "Cliente".
        usuario.setRol("cliente");
        
        //Convertimos la entidad con la que trabajamos en un dto para devilverlo(el metodo devuelve DTOs).
        return RegistroUsuarioMapper.toDto(usuarioRepository.save(usuario));
    }
    
    public boolean verificarEmail(String email){

        Usuario usuario = usuarioRepository.findByEmail(email);
        if(usuario !=null){
            return true;
        }else{
            return false;
        }
    }



   
    
}
