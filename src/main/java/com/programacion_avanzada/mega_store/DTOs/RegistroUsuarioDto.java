package com.programacion_avanzada.mega_store.DTOs;

import lombok.Data;
//Este DTO tiene estos atributos para poder hacer el registro del usuario.
@Data
public class RegistroUsuarioDto {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String contrasena;
}
