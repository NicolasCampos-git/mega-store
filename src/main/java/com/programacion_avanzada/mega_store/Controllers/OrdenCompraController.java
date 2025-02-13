package com.programacion_avanzada.mega_store.Controllers;


import com.programacion_avanzada.mega_store.DTOs.EstadoDtos.CambiarEstadoDto;
import com.programacion_avanzada.mega_store.DTOs.OrdenDtos.CrearOrdenDto;
import com.programacion_avanzada.mega_store.DTOs.OrdenDtos.OrdenCompraDto;
import com.programacion_avanzada.mega_store.Mapper.OrdenMappers.OrdenCompraMapper;
import com.programacion_avanzada.mega_store.Modelos.OrdenCompra;
import com.programacion_avanzada.mega_store.Service.Interfaces.IOrdenCompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



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
    public ResponseEntity<OrdenCompra> crearOrden(@RequestBody CrearOrdenDto crearOrdenDto) {
        OrdenCompra ordenCompra = ordenCompraService.crearOrden(crearOrdenDto.getUsuarioId(), crearOrdenDto.getProductosYCantidades());
        return ResponseEntity.ok(ordenCompra);
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
            
            return ResponseEntity.ok(ordenCompraService.obtenerOrdenes());
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

    @Operation(summary = "Eliminar orden de compra", description = "Permite eliminar una orden de compra por su ID")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOrden(@PathVariable Long id) {
        try{
            ordenCompraService.eliminarOrden(id);
            return ResponseEntity.ok("Orden eliminada");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Reactivar orden de compra", description = "Permite reactivar una orden de compra por su ID")
    @PutMapping("/reactivar/{id}")
    public ResponseEntity<String> reactivarOrden(@PathVariable Long id) {
        try{
            ordenCompraService.reactivarOrden(id);
            return ResponseEntity.ok("Orden reactivada");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Cancelar orden de compra", description = "Permite cancelar una orden de compra por su ID")
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<OrdenCompra> cancelarOrden(@PathVariable Long id) {
        try{
            
            return ResponseEntity.ok(ordenCompraService.cancelarOrden(id));
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    
    
    
}
