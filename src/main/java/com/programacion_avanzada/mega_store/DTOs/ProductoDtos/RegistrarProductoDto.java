package com.programacion_avanzada.mega_store.DTOs.ProductoDtos;

import lombok.Data;

@Data
public class RegistrarProductoDto {


    private String nombre;

    private String descripcion;

    private String tamano;

    private String color;

    private String urlImagen;

    private double precioUnitario;

    private int stock;

    private int umbralBajoStock;

    private Long marcaId;

    private Long subCategoriaId;

}
