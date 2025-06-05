package com.programacion_avanzada.mega_store.Controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.ReclamoDtos.ActualizarReclamoDto;
import com.programacion_avanzada.mega_store.DTOs.ReclamoDtos.RegistrarReclamoDto;
import com.programacion_avanzada.mega_store.Modelos.Reclamo;
import com.programacion_avanzada.mega_store.Service.ReclamoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/reclamos")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Reclamo", description = "API de reclamos")
public class ReclamoController {
   
    @Autowired
    private ReclamoService reclamoService;

    @Operation(summary = "Registrar un reclamo", description = "Este metodo permite registrar un nuevo reclamo en la base de datos")
    @PostMapping("/registrar")
    public Reclamo registrarReclamo(RegistrarReclamoDto dto) {
        return reclamoService.registrarReclamo(dto);
    }

    @Operation(summary = "Obtener un reclamo por ID", description = "Este metodo permite obtener un reclamo por su ID")
    @GetMapping("/{id}")
    public Reclamo obtenerReclamoPorId(@PathVariable long id) {
        return reclamoService.buscarPorId(id);
    }

    @Operation(summary = "Listar todos los reclamos", description = "Este metodo permite listar todos los reclamos")
    @GetMapping
    public List<Reclamo> listarReclamos() {
        return reclamoService.listarReclamos();
    }

    @Operation(summary = "Eliminar un reclamo", description = "Este metodo permite eliminar un reclamo por su ID")
    @DeleteMapping("/{id}")
    public void eliminarReclamo(@PathVariable long id) {
        reclamoService.eliminarReclamo(id);
    }

    @Operation(summary = "Reactivar un reclamo", description = "Este metodo permite reactivar un reclamo por su ID")
    @PutMapping("/reactivar/{id}")
    public void reactivarReclamo(@PathVariable long id) {
        reclamoService.reactivarReclamo(id);
    }

    @Operation(summary = "Listar reclamos por usuario", description = "Este metodo permite listar los reclamos por el ID del usuario")
    @GetMapping("/usuario/{idUsuario}")
    public List<Reclamo> listarReclamosPorUsuario(@PathVariable long idUsuario) {
        return reclamoService.listarReclamoPorUsuario(idUsuario);
    }

    @Operation(summary = "Revisar un reclamo", description = "Este metodo permite revisar un reclamo por su ID")
    @PutMapping("/revisar/{id}")
    public Reclamo revisarReclamo(@PathVariable long id) {
        return reclamoService.revisarReclamo(id);
    }

    @Operation(summary = "Aprobar un reclamo", description = "Este metodo permite aprobar un reclamo por su ID")
    @PutMapping("/aprobar/{id}")
    public Reclamo aprobarReclamo(@PathVariable long id) {
        return reclamoService.aprobarReclamo(id);
    }

    @Operation(summary = "Rechazar un reclamo", description = "Este metodo permite rechazar un reclamo por su ID")
    @PutMapping("/rechazar/{id}")
    public Reclamo rechazarReclamo(@PathVariable long id) {
        return reclamoService.rechazarReclamo(id);
    }

    @Operation(summary = "Resolver un reclamo", description = "Este metodo permite resolver un reclamo por su ID")
    @PutMapping("/resolver/{id}")
    public Reclamo resolverReclamo(@PathVariable long id) {
        return reclamoService.resolverReclamo(id);
    }

    @Operation(summary = "Actualizar un reclamo", description = "Este metodo permite actualizar un reclamo por su ID")
    @PutMapping("/actualizar/{id}")
    public Reclamo actualizarReclamo(@PathVariable long id, @RequestBody @Valid ActualizarReclamoDto dto) {
        return reclamoService.actualizarReclamo(id, dto);
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        Map<String, Object> response = reclamoService.obtenerEstadisticas(fechaInicio.atStartOfDay(), fechaFin.atStartOfDay().plusDays(1).minusSeconds(1));
        return ResponseEntity.ok(response);
    }
}
