package com.programacion_avanzada.mega_store.DTOs;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductoDto {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]+$", message = "El nombre solo puede contener letras.")
    private String nombre;

    @NotBlank
    private String descripcion;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]+$", message = "El tamaño solo puede contener letras.")
    private String tamano;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]+$", message = "El color solo puede contener letras.")
    private String color;

    @NotNull
    @Positive(message = "El precio unitario debe ser mayor que cero.")
    private double precioUnitario;

    @NotNull
    @Positive(message = "El stock debe ser mayor que cero.")
    private int stock;

    @NotNull
    @Positive(message = "El umbral bajo de stock debe ser mayor o igual que cero.")
    private int umbralBajoStock;

    @NotNull
    private MarcaDto marca;

    @NotNull
    private CategoriaDto categoria;


}
