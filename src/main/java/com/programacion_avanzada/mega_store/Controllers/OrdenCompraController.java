package com.programacion_avanzada.mega_store.Controllers;


import com.programacion_avanzada.mega_store.DTOs.EstadoDtos.CambiarEstadoDto;
import com.programacion_avanzada.mega_store.DTOs.OrdenDtos.CrearOrdenDto;
import com.programacion_avanzada.mega_store.DTOs.OrdenDtos.OrdenCompraDto;
import com.programacion_avanzada.mega_store.Mapper.OrdenMappers.OrdenCompraMapper;
import com.programacion_avanzada.mega_store.Modelos.OrdenCompra;
import com.programacion_avanzada.mega_store.Service.Interfaces.IOrdenCompraService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin(origins = "http://localhost:3000")
public class OrdenCompraController {
    @Autowired
    private IOrdenCompraService ordenCompraService;

    @Autowired
    private OrdenCompraMapper ordenCompraMapper;

    @PostMapping("/crear")
    public ResponseEntity<OrdenCompraDto> crearOrden(@RequestBody CrearOrdenDto crearOrdenDto) {
        OrdenCompraDto ordenCompraDto = ordenCompraService.crearOrden(crearOrdenDto.getUsuarioId(), crearOrdenDto.getProductosYCantidades());
        return ResponseEntity.ok(ordenCompraDto);
    }

    @PatchMapping("/cambiar-estado")
    public ResponseEntity<OrdenCompraDto> cambiarEstado(@RequestBody CambiarEstadoDto cambiarEstadoDto) {
        OrdenCompra ordenCompra = ordenCompraService.cambiarEstado(
                cambiarEstadoDto.getOrdenId(),
                cambiarEstadoDto.getNuevoEstado()
        );

        OrdenCompraDto ordenCompraDto = ordenCompraMapper.toDto(ordenCompra);

        return ResponseEntity.ok(ordenCompraDto);
    }

    @GetMapping("/orden-compra/{id}")
    public OrdenCompraDto obtenerOrdenCompra(@PathVariable Long id) {
        OrdenCompra ordenCompra = ordenCompraService.obtenerOrdenPorId(id);

        return ordenCompraMapper.toDto(ordenCompra);
    }

    //Metodo para obtener todas las ordenes
    @GetMapping("/listar")
    public ResponseEntity<List<OrdenCompra>> listarOrdenes() {
        try{
            List<OrdenCompra> ordenes = ordenCompraService.obtenerOrdenes();
            return ResponseEntity.ok(ordenes);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    //Metodo para obtener todas las ordenes de un usuario
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
