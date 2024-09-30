package com.programacion_avanzada.mega_store.Mapper;

import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.Modelos.Usuario;

public class RegistroUsuarioMapper {
    public static RegistroUsuarioDto toDto(Usuario usuario) {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setContrasena(usuario.getContrasena());
        dto.setTelefono(usuario.getTelefono()); 
        return dto;
    }

    public static Usuario toEntity(RegistroUsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasena(dto.getContrasena());
        usuario.setTelefono(dto.getTelefono()); 
        return usuario;
    }
}

