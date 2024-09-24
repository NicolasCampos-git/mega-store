package com.programacion_avanzada.mega_store.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrarProductoDto {
    @NotBlank
    private String nombre;

    @NotBlank
    
    private String descripcion;

    @NotBlank
    private String tamano;

    @NotBlank
    private String color;

    @NotNull
    private double precioUnitario;

    @NotNull
    private int stock;

    @NotNull
    private int umbralBajoStock;

    @NotNull
    private Long marcaId;

    @NotNull
    private Long CategoriaId;
}
