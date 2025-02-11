package com.programacion_avanzada.mega_store.Service.Interfaces;

import com.programacion_avanzada.mega_store.DTOs.AuthDtos.InicioSesionDTO;
import com.programacion_avanzada.mega_store.DTOs.AuthDtos.RecuperarContrasenaDto;
import com.programacion_avanzada.mega_store.Modelos.Usuario;

public interface ISesionService {
    
    Usuario iniciarSesion(InicioSesionDTO inicioSesion);

    Usuario recuperarContrasena(RecuperarContrasenaDto recuperarContrasenaDto);

    
    

}
