package com.programacion_avanzada.mega_store.DTOs.SubcategoriaDtos;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDtos.CategoriaDto;

import lombok.Data;


@Data
public class SubCategoriaDTO{


    private long id;

    private String nombre;

    private String descripcion;

    private CategoriaDto categoriaDto;

    private boolean estaActivo;
}