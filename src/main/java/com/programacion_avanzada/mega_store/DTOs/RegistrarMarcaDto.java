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
public class RegistrarMarcaDto {
    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 64, message = "El nombre debe tener entre 2 y 64 caracteres.")
    @Pattern(regexp = "^[^\\d\\s]+$", message = "El nombre no debe contener espacios ni números.")
    private String nombre;


    @NotBlank(message = "La descripción no puede estar vacía.")
    @Size(min = 2, max = 64, message = "La descripción debe tener entre 2 y 64 caracteres.")
    @Pattern(regexp = "^[^\\d]*$", message = "La descripción no debe contener números.")
    private String descripcion;

    //Metodo que valida los campos de acuerdo con las anotaciones
    public boolean esValido() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<RegistrarMarcaDto>> violations = validator.validate(this);
        return violations.isEmpty();
    }
}
