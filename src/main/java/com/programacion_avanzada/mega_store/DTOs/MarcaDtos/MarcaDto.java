package com.programacion_avanzada.mega_store.DTOs.MarcaDtos;

import lombok.Data;


@Data
public class MarcaDto {

    private long id;

    private String nombre;

    private String descripcion;

    private boolean estaActivo;

}
