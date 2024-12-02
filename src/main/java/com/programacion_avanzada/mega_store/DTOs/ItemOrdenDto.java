package com.programacion_avanzada.mega_store.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ItemOrdenDto {
    private String productoNombre;
    private Integer cantidad;
    private Double subtotal;
}
