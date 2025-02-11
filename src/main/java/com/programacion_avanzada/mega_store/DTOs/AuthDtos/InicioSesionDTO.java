package com.programacion_avanzada.mega_store.DTOs.AuthDtos;

import lombok.Data;

@Data
public class InicioSesionDTO {
    
    private String email;
    private String contrasena;
}
