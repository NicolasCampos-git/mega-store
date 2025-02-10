package com.programacion_avanzada.mega_store.DTOs;


import lombok.Data;

@Data
public class ProductoDto {


    private long id;
    private String nombre;
    private String descripcion;
    private String tamano;
    private String color;
    private String urlImagen;
    private double precioUnitario;
    private int stock;
    private int umbralBajoStock;
    private MarcaDto marca;
    private SubCategoriaDTO subCategoria;
    private boolean estaActivo;

}
