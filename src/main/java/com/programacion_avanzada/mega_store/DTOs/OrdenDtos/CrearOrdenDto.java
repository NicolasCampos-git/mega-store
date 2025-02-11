package com.programacion_avanzada.mega_store.DTOs.OrdenDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Data
public class CrearOrdenDto {
    private Long usuarioId;
    private Map<Long, Integer> productosYCantidades;
}
