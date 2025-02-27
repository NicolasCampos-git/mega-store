package com.programacion_avanzada.mega_store.DTOs.UsuarioDtos;



import lombok.Data;

@Data
public class RegistroUsuarioDto {

    
    private String nombre;

    
    private String apellido;

    private String telefono;

    private String email;

  
    private String contrasena;

    private String contrasenaRepetida;

}
