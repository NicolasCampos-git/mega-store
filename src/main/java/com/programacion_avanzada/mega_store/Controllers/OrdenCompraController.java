package com.programacion_avanzada.mega_store.Controllers;


import com.programacion_avanzada.mega_store.DTOs.EstadoDtos.CambiarEstadoDto;
import com.programacion_avanzada.mega_store.DTOs.OrdenDtos.CrearOrdenDto;
import com.programacion_avanzada.mega_store.DTOs.OrdenDtos.OrdenCompraDto;
import com.programacion_avanzada.mega_store.Mapper.OrdenMappers.OrdenCompraMapper;
import com.programacion_avanzada.mega_store.Modelos.OrdenCompra;
import com.programacion_avanzada.mega_store.Service.Interfaces.IOrdenCompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "OrdenCompra", description = "API de ordenes de compra")
public class OrdenCompraController {
    @Autowired
    private IOrdenCompraService ordenCompraService;

    @Autowired
    private OrdenCompraMapper ordenCompraMapper;

    @Operation(summary = "Crear orden de compra", description = "Permite crear una orden de compra")
    @PostMapping("/crear")
    public ResponseEntity<OrdenCompraDto> crearOrden(@RequestBody CrearOrdenDto crearOrdenDto) {
        OrdenCompraDto ordenCompraDto = ordenCompraService.crearOrden(crearOrdenDto.getUsuarioId(), crearOrdenDto.getProductosYCantidades());
        return ResponseEntity.ok(ordenCompraDto);
    }

    @Operation(summary = "Cambiar estado de orden de compra", description = "Permite cambiar el estado de una orden de compra")
    @PatchMapping("/cambiar-estado")
    public ResponseEntity<OrdenCompraDto> cambiarEstado(@RequestBody CambiarEstadoDto cambiarEstadoDto) {
        OrdenCompra ordenCompra = ordenCompraService.cambiarEstado(
                cambiarEstadoDto.getOrdenId(),
                cambiarEstadoDto.getNuevoEstado()
        );

        OrdenCompraDto ordenCompraDto = ordenCompraMapper.toDto(ordenCompra);

        return ResponseEntity.ok(ordenCompraDto);
    }

    @Operation(summary = "Obtener orden de compra por ID", description = "Permite obtener una orden de compra por su ID")
    @GetMapping("/orden-compra/{id}")
    public OrdenCompraDto obtenerOrdenCompra(@PathVariable Long id) {
        OrdenCompra ordenCompra = ordenCompraService.obtenerOrdenPorId(id);

        return ordenCompraMapper.toDto(ordenCompra);
    }

    @Operation(summary = "Listar ordenes de compra", description = "Permite listar todas las ordenes de compra registradas en la base de datos")
    @GetMapping("/listar")
    public ResponseEntity<List<OrdenCompra>> listarOrdenes() {
        try{
            List<OrdenCompra> ordenes = ordenCompraService.obtenerOrdenes();
            return ResponseEntity.ok(ordenes);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Listar ordenes de compra por usuario", description = "Permite listar todas las ordenes de compra de un usuario")
    @GetMapping("/listar/{id}")
    public ResponseEntity<List<OrdenCompra>> listarOrdenesPorUsuario(@PathVariable Long id) {
        try{
            List<OrdenCompra> ordenes = ordenCompraService.obtenerOrdenesPorUsuario(id);
            return ResponseEntity.ok(ordenes);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    
}
