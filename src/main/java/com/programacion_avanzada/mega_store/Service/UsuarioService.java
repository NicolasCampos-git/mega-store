
package com.programacion_avanzada.mega_store.Service;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDto;
import com.programacion_avanzada.mega_store.Mapper.RegistroUsuarioMapper;
import com.programacion_avanzada.mega_store.Mapper.UsuarioMapper;
import com.programacion_avanzada.mega_store.Modelos.Usuario;

import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;

import ch.qos.logback.core.util.StringUtil;


@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ISenderService senderService;

    /*
     * Metodo encargado de registrar un cliente en la web,
     * verificando que el correo no este previamente registrado
     * ademas, normaliza los datos.
     */
    @Override
    public RegistroUsuarioDto registrarUsuario(RegistroUsuarioDto dto) {

        if(dto.getContrasena().equals(dto.getContrasenaRepetida())){
            
            Usuario usuario = RegistroUsuarioMapper.toEntity(dto);
            
            
            if(usuarioRepository.existsByEmail(usuario.getEmail())){
                throw new IllegalArgumentException("El email ya está registrado.");
            }

            //Nrmalizacion de datos
            usuario.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase()));
            usuario.setApellido(StringUtil.capitalizeFirstLetter(dto.getApellido().toLowerCase()));
            usuario.setEmail(dto.getEmail().trim().toLowerCase());
            usuario.setTelefono(dto.getTelefono());

            //Por temas de practicidad agrega por defecto el rol "Cliente".
            usuario.setRol("cliente");
            senderService.enviarCorreo(usuario.getEmail());

            
            return RegistroUsuarioMapper.toDto(usuarioRepository.save(usuario));
        }else{
            throw new IllegalArgumentException("Las contrasenas no coinciden");
        }
    }

    /*
     * Metodo encargado de actualizar los datos del cliente.
     */
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

    public UsuarioDto buscarPorId(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + id));
        return UsuarioMapper.toDto(usuario);
    }

    // Método para listar todos los usuarios
    public List<UsuarioDto> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    // Método para "eliminar" un usuario (desactivar)
    public void eliminarUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + id));
        usuario.setEstaActivo(false);  // Marcar el usuario como inactivo
        usuarioRepository.save(usuario);
    }

    // Método para "reactivar" un usuario (activar)
    public void reactivarUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + id));
        usuario.setEstaActivo(true);  // Marcar el usuario como activo
        usuarioRepository.save(usuario);
    }


    
}
