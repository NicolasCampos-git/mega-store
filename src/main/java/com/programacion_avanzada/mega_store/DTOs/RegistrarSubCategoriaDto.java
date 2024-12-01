package com.programacion_avanzada.mega_store.DTOs;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Data;

import java.util.Set;

@Data
public class RegistrarSubCategoriaDto {

    private String nombre;

    private String descripcion;

    private Long categoriaId;

    //Metodo que valida los campos de acuerdo con las anotaciones
    public boolean esValido() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<RegistrarSubCategoriaDto>> violations = validator.validate(this);
        return violations.isEmpty();
    }

}
