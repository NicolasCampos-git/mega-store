package com.programacion_avanzada.mega_store.DTOs;

import lombok.Data;
//Este DTO es en caso de queter motrar datos del usuario.
@Data
public class UsuarioDto {
    
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String rol;

}
