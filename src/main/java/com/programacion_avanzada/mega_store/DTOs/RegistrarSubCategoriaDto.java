package com.programacion_avanzada.mega_store.DTOs;


import lombok.Data;


@Data
public class RegistrarSubCategoriaDto {

    private String nombre;

    private String descripcion;

    private Long categoriaId;


}
