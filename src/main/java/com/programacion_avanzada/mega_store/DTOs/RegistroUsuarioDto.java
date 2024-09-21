package com.programacion_avanzada.mega_store.DTOs;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
//Este DTO tiene estos atributos para poder hacer el registro del usuario.
@Data
public class RegistroUsuarioDto {
    
    @NotNull //Valida que no sea nulo
    @NotBlank //Valida que no este en blanco.
    @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúñÑ]{1,20}$", message = "El nombre solo puede contener letras, sin espacios, con un máximo de 20 caracteres")
    private String nombre;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúñÑ]{1,20}$", message = "El apellido solo puede contener letras, sin espacios, con un máximo de 20 caracteres")
    private String apellido;

    @NotNull
    @NotBlank
    // @Pattern(regexp = "^[0-9]{9,15}$", message = "El teléfono solo puede contener números, con una longitud de entre 9 y 15 dígitos")
    private String telefono;

    @NotNull
    @NotBlank
    @Email //Valida que tenga formato de email
    private String email;

    @NotBlank
    @NotNull
    // @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluir una mayúscula, una minúscula, un número y un carácter especial")
    private String contrasena;

    @NotBlank
    @NotNull
    // @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluir una mayúscula, una minúscula, un número y un carácter especial")
    private String contrasenaRepetida;
}
