package com.programacion_avanzada.mega_store.DTOs;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class MarcaDto {

    private long id;

    @NotBlank(message = "El nombre no debe estar vacío.")
    @Size(min = 2, max = 64)
    @Pattern(regexp = "^[^\\d]*$", message = "El nombre no debe contener dígitos.")
    @Pattern(regexp = "^[^\\s]*$", message = "El nombre no debe contener espacios en blanco.")
    private String nombre;

    // Permitir nulo o vacío en la descripción
    @Size(max = 100, message = "La descripción no debe exceder los 100 caracteres.")
    @Pattern(regexp = "^[^\\d]*$", message = "La descripción no debe contener dígitos.")
    private String descripcion;

    private boolean estaActivo;

    // Método que valida los campos de acuerdo con las anotaciones
    public boolean esValido() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<MarcaDto>> violations = validator.validate(this);
        return violations.isEmpty();
    }
}
