package com.programacion_avanzada.mega_store.Service;



import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDto;



public interface IUsuarioService {
    
    public RegistroUsuarioDto registrarUsuario(RegistroUsuarioDto dto);

    public UsuarioDto actualizarInformacionPersonal(long id, UsuarioDto dto);
    
}
