package com.programacion_avanzada.mega_store.Service;

import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDto;
import com.programacion_avanzada.mega_store.Modelos.Usuario;

import java.util.List;

public interface IUsuarioService {

    // Métodos previos
    RegistroUsuarioDto registrarUsuario(RegistroUsuarioDto dto);

    UsuarioDto actualizarInformacionPersonal(long id, UsuarioDto dto);

    // Nuevos métodos agregados
    UsuarioDto buscarPorId(long id);  // Buscar usuario por ID

    List<UsuarioDto> listarUsuarios();  // Listar todos los usuarios

    void eliminarUsuario(long id);  // Eliminar (desactivar) un usuario

    void reactivarUsuario(long id);  // Reactivar un usuario

    Usuario asignarDireccionComoPrincial(long idUsuario, long idDireccion);


}