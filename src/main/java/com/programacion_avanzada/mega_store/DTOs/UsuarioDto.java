package com.programacion_avanzada.mega_store.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
//Este DTO es en caso de queter motrar datos del usuario.
@Data
public class UsuarioDto {

    @NotNull 
    @NotBlank 
    @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúñÑ]{2,20}$", message = "El nombre solo puede contener letras, sin espacios, con un máximo de 20 caracteres")
    private String nombre;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúñÑ]{2,20}$", message = "El apellido solo puede contener letras, sin espacios, con un máximo de 20 caracteres")
    private String apellido;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{9,15}$", message = "El teléfono solo puede contener números, con una longitud de entre 9 y 15 dígitos")
    private String telefono;

    @NotNull
    @NotBlank
    private String rol;

}
