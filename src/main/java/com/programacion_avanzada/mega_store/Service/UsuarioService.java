package com.programacion_avanzada.mega_store.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.Mapper.RegistroUsuarioMapper;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public RegistroUsuarioDto registrarUsuario(RegistroUsuarioDto dto) {
        Usuario usuario = RegistroUsuarioMapper.toEntity(dto);

        if(verificarEmail(usuario.getEmail()) == true){
            return null;
        }

        usuario.setRol("Cliente");

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
