package com.programacion_avanzada.mega_store.DTOs;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Data;

import java.util.Set;

@Data
public class SubCategoriaDTO{


    private long id;

    private String nombre;

    private String descripcion;

    private CategoriaDto categoriaDto;

    private boolean estaActivo;
}