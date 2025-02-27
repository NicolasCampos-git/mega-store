package com.programacion_avanzada.mega_store.DTOs.ReclamoDtos;


import lombok.Data;

@Data
public class RegistrarReclamoDto {
    
    
    private String motivo;
    private String descripcion;

    private long idTipoReclamo;
    private long idUsuario;
    private long idOrdenCompra;



}
