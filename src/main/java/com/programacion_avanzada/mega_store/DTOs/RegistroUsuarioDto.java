package com.programacion_avanzada.mega_store.DTOs;

import lombok.Data;

@Data
public class RegistroUsuarioDto {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String contrasena;
}
