package com.programacion_avanzada.mega_store.DTOs;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class RegistrarProductoDto {


    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 64, message = "El nombre debe tener entre 2 y 64 caracteres.")
    @Pattern(regexp = "^[^\\d\\s]+$", message = "El nombre no debe contener espacios ni números.")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria.")
    @Size(min = 2, max = 100, message = "La descripción debe tener entre 2 y 100 caracteres.")
    @Pattern(regexp = "^[^\\d]*$", message = "La descripción no debe contener números.")
    private String descripcion;

    @NotNull(message = "El tamaño es obligatorio.")
    @Size(min = 1, max = 10, message = "El tamaño debe tener entre 1 y 10 caracteres.")
    private String tamano;

    @NotBlank(message = "El color es obligatorio.")
    @Size(min = 2, max = 5, message = "El color debe tener entre 2 y 5 caracteres.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "El color no debe contener números.")
    private String color;

    @NotNull(message = "El precio unitario es obligatorio.")
    @Positive(message = "El precio unitario debe ser un valor positivo.")
    private double precioUnitario;

    @NotNull(message = "El stock es obligatorio.")
    @Positive(message = "El stock debe ser un valor positivo.")
    private int stock;

    @NotNull(message = "El umbral bajo de stock es obligatorio.")
    @Positive(message = "El umbral bajo de stock debe ser un valor positivo.")
    private int umbralBajoStock;

    @NotNull(message = "La marca es obligatoria.")
    @Positive(message = "La marca debe ser un valor positivo.")
    private Long marcaId;

    @NotNull(message = "La subcategoría es obligatoria.")
    @Positive(message = "La subcategoría debe ser un valor positivo.")
    private Long subCategoriaId;

    //Metodo que valida los campos de acuerdo con las anotaciones
    public boolean esValido() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<RegistrarProductoDto>> violations = validator.validate(this);
        return violations.isEmpty();
    }
}
