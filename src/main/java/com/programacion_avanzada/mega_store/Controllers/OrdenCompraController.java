package com.programacion_avanzada.mega_store.Controllers;

import com.programacion_avanzada.mega_store.DTOs.*;
import com.programacion_avanzada.mega_store.Modelos.OrdenCompra;
import com.programacion_avanzada.mega_store.Service.IOrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin(origins = "http://localhost:5173")
public class OrdenCompraController {
    @Autowired
    private IOrdenCompraService ordenCompraService;

    @PostMapping("/crear")
    public ResponseEntity<OrdenCompraDto> crearOrden(@RequestBody CrearOrdenDto crearOrdenDto) {
        // Llamamos al servicio para crear la orden
        OrdenCompraDto ordenCompraDto = ordenCompraService.crearOrden(crearOrdenDto.getUsuarioId(), crearOrdenDto.getProductosYCantidades());

        return ResponseEntity.ok(ordenCompraDto);
    }

    @PatchMapping("/cambiar-estado")
    public ResponseEntity<OrdenCompraDto> cambiarEstado(@RequestBody CambiarEstadoDto cambiarEstadoDto) {
        OrdenCompra ordenCompra = ordenCompraService.cambiarEstado(
                cambiarEstadoDto.getOrdenId(),
                cambiarEstadoDto.getNuevoEstado()
        );

        // Mapear la entidad OrdenCompra a OrdenCompraDto
        OrdenCompraDto ordenCompraDto = new OrdenCompraDto();
        ordenCompraDto.setId(ordenCompra.getId());
        ordenCompraDto.setFecha(ordenCompra.getFecha());
        ordenCompraDto.setTotal(ordenCompra.getTotal());
        ordenCompraDto.setEstado(ordenCompra.getEstado().getNombre());
        ordenCompraDto.setUsuarioId(ordenCompra.getUsuario().getId());

        // Mapear los items de la orden a ItemOrdenDto
        List<ItemOrdenDto> productosYCantidades = ordenCompra.getItems().stream()
                .map(item -> {
                    ItemOrdenDto itemDto = new ItemOrdenDto();
                    itemDto.setProductoNombre(item.getProducto().getNombre());
                    itemDto.setCantidad(item.getCantidad());
                    itemDto.setSubtotal(item.getSubtotal());
                    return itemDto;
                })
                .collect(Collectors.toList());

        ordenCompraDto.setProductosYCantidades(productosYCantidades);

        return ResponseEntity.ok(ordenCompraDto);
    }

    @GetMapping("/orden-compra/{id}")
    public OrdenCompraDto obtenerOrdenCompra(@PathVariable Long id) {
        // Llamamos al servicio para obtener la orden de compra
        OrdenCompra ordenCompra = ordenCompraService.obtenerOrdenPorId(id);

        // Creamos el DTO para la respuesta
        OrdenCompraDto ordenCompraDto = new OrdenCompraDto();
        ordenCompraDto.setId(ordenCompra.getId());
        ordenCompraDto.setFecha(ordenCompra.getFecha());
        ordenCompraDto.setTotal(ordenCompra.getTotal());
        ordenCompraDto.setEstado(ordenCompra.getEstado().getNombre());
        ordenCompraDto.setUsuarioId(ordenCompra.getUsuario().getId());

        // Convertir los items de la orden en un list de ItemOrdenDto
        List<ItemOrdenDto> productosYCantidades = ordenCompra.getItems().stream()
                .map(item -> {
                    ItemOrdenDto itemDto = new ItemOrdenDto();
                    itemDto.setProductoNombre(item.getProducto().getNombre());  // Asumiendo que 'item' tiene un 'producto' con un campo 'nombre'
                    itemDto.setCantidad(item.getCantidad());
                    itemDto.setSubtotal(item.getSubtotal());
                    return itemDto;
                })
                .collect(Collectors.toList());

        ordenCompraDto.setProductosYCantidades(productosYCantidades);

        return ordenCompraDto;
    }
}
