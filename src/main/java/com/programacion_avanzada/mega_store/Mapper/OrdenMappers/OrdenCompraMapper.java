package com.programacion_avanzada.mega_store.Mapper.OrdenMappers;

import com.programacion_avanzada.mega_store.DTOs.ItemOrdenDtos.ItemOrdenDto;
import com.programacion_avanzada.mega_store.DTOs.OrdenDtos.OrdenCompraDto;
import com.programacion_avanzada.mega_store.Modelos.ItemOrden;
import com.programacion_avanzada.mega_store.Modelos.OrdenCompra;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdenCompraMapper {
    public OrdenCompraDto toDto(OrdenCompra ordenCompra) {
        OrdenCompraDto dto = new OrdenCompraDto();
        dto.setId(ordenCompra.getId());
        dto.setFecha(ordenCompra.getFecha());
        dto.setTotal(ordenCompra.getTotal());
        dto.setEstado(ordenCompra.getEstado().getNombre());
        dto.setUsuarioId(ordenCompra.getUsuario().getId());

        // Mapear los items
        List<ItemOrdenDto> itemDtos = ordenCompra.getItems().stream()
                .map(this::toItemOrdenDto)
                .collect(Collectors.toList());
        dto.setProductosYCantidades(itemDtos);

        return dto;
    }

    private ItemOrdenDto toItemOrdenDto(ItemOrden item) {
        ItemOrdenDto dto = new ItemOrdenDto();
        dto.setProductoNombre(item.getProducto().getNombre());
        dto.setCantidad(item.getCantidad());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }
}
