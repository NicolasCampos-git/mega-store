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


    private String nombre;

    private String descripcion;

    private String tamano;

    private String color;

    private double precioUnitario;

    private int stock;

    private int umbralBajoStock;

    private Long marcaId;

    private Long subCategoriaId;

}
