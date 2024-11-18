package com.programacion_avanzada.mega_store.DTOs;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

//Este DTO es en caso de queter motrar datos del usuario.
@Data
public class UsuarioDto {

    @NotBlank(message = "El nombre es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúñÑ]{2,64}$", message = "El nombre solo puede contener letras, sin espacios, con un máximo de 64 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúñÑ]{2,64}$", message = "El apellido solo puede contener letras, sin espacios, con un máximo de 64 caracteres.")
    private String apellido;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Pattern(regexp = "^[0-9]{9,15}$", message = "El teléfono solo puede contener números, con una longitud de entre 9 y 15 dígitos.")
    private String telefono;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "Debe ingresar un formato de email válido.")
    private String email;

    @NotBlank
    private String rol;

    //Metodo que valida los campos de acuerdo con las anotaciones
    public boolean esValido() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<UsuarioDto>> violations = validator.validate(this);
        return violations.isEmpty();
    }

}
