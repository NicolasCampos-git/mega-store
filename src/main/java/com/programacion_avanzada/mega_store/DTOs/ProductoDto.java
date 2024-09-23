package com.programacion_avanzada.mega_store.DTOs;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductoDto {

    @NotBlank
    private String nombre;

    @NotBlank
    
    private String descripcion;

    @NotBlank
    private String Tamano;

    @NotBlank
    private String color;

    @NotNull
    private double precioUnitario;

    @NotNull
    private int stock;

    @NotNull
    private int umbralBajoStock;

    @NotNull
    private MarcaDto marca;

    @NotNull
    private CategoriaDto categoria;


}
