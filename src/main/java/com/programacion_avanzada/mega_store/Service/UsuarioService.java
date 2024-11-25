
package com.programacion_avanzada.mega_store.Service;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDto;
import com.programacion_avanzada.mega_store.Mapper.RegistroUsuarioMapper;
import com.programacion_avanzada.mega_store.Mapper.UsuarioMapper;
import com.programacion_avanzada.mega_store.Modelos.DireccionEnvio;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.DireccionEnvioRepository;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;

import ch.qos.logback.core.util.StringUtil;


@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    DireccionEnvioRepository direccionRepository;

    @Autowired
    ISenderService senderService;

    /*
     * Metodo encargado de registrar un cliente en la web,
     * verificando que el correo no este previamente registrado
     * ademas, normaliza los datos.
     */
    @Override
    public RegistroUsuarioDto registrarUsuario(RegistroUsuarioDto dto) {

        valirdarContrasena(dto.getContrasena(), dto.getContrasenaRepetida());
        Usuario usuario = RegistroUsuarioMapper.toEntity(dto);
        valirdarMailRepetido(usuario.getEmail());
        valirdarNombre(usuario.getNombre());
        valirdarApellido(usuario.getApellido());
        valirdarTelefono(usuario.getTelefono());    
        valirdarEmail( usuario.getEmail());
        valirdarContrasena(dto.getContrasena(), dto.getContrasenaRepetida());

            
        

        //Nrmalizacion de datos
        normalizarDatos(usuario);

        //Por temas de practicidad agrega por defecto el rol "Cliente".
        usuario.setRol("cliente");
        senderService.enviarCorreo(usuario.getEmail());

            
        return RegistroUsuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    /*
     * Metodo encargado de actualizar los datos del cliente.
     */
    @Override
    public UsuarioDto actualizarInformacionPersonal(long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con el ID: " + id);
            
        }
    
        
        usuario.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase()));
        usuario.setApellido(StringUtil.capitalizeFirstLetter(dto.getApellido().toLowerCase()));
        usuario.setEmail(dto.getEmail().trim().toLowerCase());
        usuario.setTelefono(dto.getTelefono());
    
        
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return UsuarioMapper.toDto(usuarioActualizado);
    }

    @Override 
    public UsuarioDto buscarPorId(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + id));
        return UsuarioMapper.toDto(usuario);
    }

    // Método para listar todos los usuarios
    @Override
    public List<UsuarioDto> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    // Método para "eliminar" un usuario (desactivar)
    @Override
    public void eliminarUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + id));
        usuario.setEstaActivo(false);  // Marcar el usuario como inactivo
        usuarioRepository.save(usuario);
    }

    // Método para "reactivar" un usuario (activar)
    @Override
    public void reactivarUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + id));
        usuario.setEstaActivo(true);  // Marcar el usuario como activo
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario asignarDireccionComoPrincial(long idUsuario, long idDireccion) {
        Usuario usuario = buscarUsuario(idUsuario);


        for(DireccionEnvio direcciones: usuario.getDirecciones()) {
            if(direcciones.getId() == idDireccion) {
                direcciones.setEsPrincipal(true);
                direccionRepository.save(direcciones);
                
            }else{
                direcciones.setEsPrincipal(false);
                direccionRepository.save(direcciones);
            }
        }
        usuarioRepository.save(usuario);

        
        return usuario.getDirecciones().stream().filter(direccionEnvio -> direccionEnvio.isEsPrincipal()).findFirst().get().getUsuario();
    }


    private Usuario buscarUsuario(long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con el ID: " + idUsuario);
        }        
        return usuario;
    }

    private void valirdarNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        if (nombre.length() < 2 || nombre.length() > 64) {
            throw new IllegalArgumentException("El nombre debe tener entre 2 y 64 caracteres.");
            
        }
        if(nombre.contains(" ")) {
            throw new IllegalArgumentException("El nombre no debe contener espacios.");
        }
    }

    private void valirdarApellido(String apellido) {
        if (apellido == null || apellido.isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacio.");
        }
        if (apellido.length() < 2 || apellido.length() > 64) {
            throw new IllegalArgumentException("El apellido debe tener entre 2 y 64 caracteres.");
            
        }
        if(apellido.contains(" ")) {
            throw new IllegalArgumentException("El apellido no debe contener espacios.");
        }
    }

    private void valirdarTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("El telefono no puede estar vacio.");
        }
        if (telefono.length() < 9 || telefono.length() > 15) {
            throw new IllegalArgumentException("El telefono debe tener entre 9 y 15 caracteres.");
            
        }
        if(!telefono.matches("^[0-9]+$")) {
            throw new IllegalArgumentException("El telefono solo debe contener numeros.");
        }
        if (telefono.contains(" ")) {
            throw new IllegalArgumentException("El telefono no debe contener espacios.");
            
        }
        
    }

    private void valirdarEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacio.");
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El email no es valido.");
        }
        if (email.contains(" ")) {
            throw new IllegalArgumentException("El email no debe contener espacios.");
        }
        if ( email.length() < 2 ||email.length() > 64) {
            throw new IllegalArgumentException("El email debe tener menos de 64 caracteres.");
            
        }

    }
    private void valirdarMailRepetido(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya esta registrado.");
        }
    }

    private void valirdarContrasena(String contrasena, String contrasenaRepetida) {
        if (contrasena == null || contrasena.isEmpty()) {
            throw new IllegalArgumentException("La contrasena no puede estar vacia.");
        }
        if (contrasena.length() < 8) {
            throw new IllegalArgumentException("La contrasena debe tener al menos 8 caracteres.");
        }
        if (!contrasena.matches("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            throw new IllegalArgumentException("La contrasena debe tener al menos una mayuscula y un numero.");
        }
        if (!contrasena.equals(contrasenaRepetida)) {
            throw new IllegalArgumentException("Las contrasenas no coinciden.");
        }
    }

    private void normalizarDatos(Usuario usuario) {
        usuario.setNombre(StringUtil.capitalizeFirstLetter(usuario.getNombre().toLowerCase()));
        usuario.setApellido(StringUtil.capitalizeFirstLetter(usuario.getApellido().toLowerCase()));
        usuario.setEmail(usuario.getEmail().trim().toLowerCase());
    }
    


    
}
