package com.programacion_avanzada.mega_store.Service.Interfaces;

import com.programacion_avanzada.mega_store.DTOs.UsuarioDtos.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDtos.UsuarioDto;
import com.programacion_avanzada.mega_store.Modelos.Usuario;

import java.util.List;

public interface IUsuarioService {

    // Métodos previos
    Usuario registrarUsuario(RegistroUsuarioDto dto);

    Usuario actualizarInformacionPersonal(long id, UsuarioDto dto);

    // Nuevos métodos agregados
    Usuario buscarPorId(long id);  // Buscar usuario por ID

    List<Usuario> listarUsuarios();  // Listar todos los usuarios

    void eliminarUsuario(long id);  // Eliminar (desactivar) un usuario

    void reactivarUsuario(long id);  // Reactivar un usuario

    Usuario asignarDireccionComoPrincial(long idUsuario, long idDireccion);

    


}