package com.programacion_avanzada.mega_store.DTOs;


import jakarta.validation.constraints.NotNull;

import lombok.Data;

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
