package com.programacion_avanzada.mega_store.Service;


import com.programacion_avanzada.mega_store.DTOs.DireccionEnvioDto;
import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.Modelos.Usuario;

public interface IUsuarioService {
    
    public RegistroUsuarioDto registrarUsuario(RegistroUsuarioDto dto);

    public Usuario agregarDireccionEnvio(long id, DireccionEnvioDto dto);
}
