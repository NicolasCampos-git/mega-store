package com.programacion_avanzada.mega_store.Controllers;

import com.programacion_avanzada.mega_store.DTOs.*;
import com.programacion_avanzada.mega_store.Mapper.OrdenCompraMapper;
import com.programacion_avanzada.mega_store.Modelos.OrdenCompra;
import com.programacion_avanzada.mega_store.Service.IOrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin(origins = "http://localhost:5173")
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
}
