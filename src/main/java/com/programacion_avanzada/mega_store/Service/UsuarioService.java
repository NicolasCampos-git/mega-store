package com.programacion_avanzada.mega_store.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDto;
import com.programacion_avanzada.mega_store.Mapper.RegistroUsuarioMapper;
import com.programacion_avanzada.mega_store.Mapper.UsuarioMapper;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.DireccionEnvioRepository;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;

import ch.qos.logback.core.util.StringUtil;


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
        
        //Verifica que el mail no este registrado
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new IllegalArgumentException("El email ya está registrado.");
        }

        //Nrmalizacion de datos
        usuario.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase()));
        usuario.setApellido(StringUtil.capitalizeFirstLetter(dto.getApellido().toLowerCase()));
        usuario.setEmail(dto.getEmail().trim().toLowerCase());

        //Por temas de practicidad agrega por defecto el rol "Cliente".
        usuario.setRol("cliente");
        
        //Convertimos la entidad con la que trabajamos en un dto para devilverlo(el metodo devuelve DTOs).
        return RegistroUsuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDto actualizarInformacionPersonal(long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow();
    
        
        usuario.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase()));
        usuario.setApellido(StringUtil.capitalizeFirstLetter(dto.getApellido().toLowerCase()));
        usuario.setEmail(dto.getEmail().trim().toLowerCase());
        usuario.setTelefono(dto.getTelefono());
    
        
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return UsuarioMapper.toDto(usuarioActualizado);
    }


    
}
