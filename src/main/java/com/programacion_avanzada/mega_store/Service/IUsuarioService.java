package com.programacion_avanzada.mega_store.Service;

import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDto;

import java.util.List;

public interface IUsuarioService {

    // Métodos previos
    public RegistroUsuarioDto registrarUsuario(RegistroUsuarioDto dto);

    public UsuarioDto actualizarInformacionPersonal(long id, UsuarioDto dto);

    // Nuevos métodos agregados
    public UsuarioDto buscarPorId(long id);  // Buscar usuario por ID

    public List<UsuarioDto> listarUsuarios();  // Listar todos los usuarios

    public void eliminarUsuario(long id);  // Eliminar (desactivar) un usuario

    public void reactivarUsuario(long id);  // Reactivar un usuario
}