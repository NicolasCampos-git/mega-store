package com.programacion_avanzada.mega_store.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.DireccionEnvioDto;
import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.Mapper.DireccionEnvioMapper;
import com.programacion_avanzada.mega_store.Mapper.RegistroUsuarioMapper;
import com.programacion_avanzada.mega_store.Modelos.DireccionEnvio;
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

    //Metodo que agrega direcciones al usuario.
    @Override
    public Usuario agregarDireccionEnvio(long id, DireccionEnvioDto dto) {

        //Traemos los datos del usuario que coincida con el ID.
        Usuario usuario = usuarioRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado")); //En caso de no encontrarlo devuelve una exception.

        // Mapeamos el DTO a la entidad DirecciónEnvio
        DireccionEnvio direccion = DireccionEnvioMapper.toEntity(dto);
        
        // Añadimos la dirección a la lista de direcciones del usuario
        usuario.setDireccion(direccion);  // Método que gestiona la bidireccionalidad
        
        // Guardamos el usuario con las direcciones actualizadas
        return usuarioRepository.save(usuario);
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
