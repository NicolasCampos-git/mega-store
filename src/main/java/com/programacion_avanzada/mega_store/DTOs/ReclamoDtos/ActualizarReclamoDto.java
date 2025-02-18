package com.programacion_avanzada.mega_store.DTOs.ReclamoDtos;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarReclamoDto {

    private long id;

    
    private String motivo;

    
    private String descripcion;

    
    private long idTipoReclamo;

    
    private long idUsuario;

    
    private long idOrdenCompra;
}
