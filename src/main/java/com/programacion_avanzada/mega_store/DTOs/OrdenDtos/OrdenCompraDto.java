package com.programacion_avanzada.mega_store.DTOs.OrdenDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import com.programacion_avanzada.mega_store.DTOs.ItemOrdenDtos.ItemOrdenDto;

@Getter
@Setter
@Data
public class OrdenCompraDto {
    private Long id;
    private LocalDateTime fecha;
    private Double total;
    private String estado;
    private Long usuarioId;
    private List<ItemOrdenDto> productosYCantidades;
}
