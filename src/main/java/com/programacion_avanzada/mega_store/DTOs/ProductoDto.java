package com.programacion_avanzada.mega_store.DTOs;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.Set;

@Data
public class ProductoDto {


    private long id;

    private String nombre;

    private String descripcion;

    private String tamano;

    private String color;

    private double precioUnitario;

    private int stock;

    private int umbralBajoStock;

    private MarcaDto marca;

    private SubCategoriaDTO subCategoria;

    @NotNull
    private boolean estaActivo;

}
