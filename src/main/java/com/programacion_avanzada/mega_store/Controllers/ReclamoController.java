package com.programacion_avanzada.mega_store.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.ReclamoDtos.RegistrarReclamoDto;
import com.programacion_avanzada.mega_store.Modelos.Reclamo;
import com.programacion_avanzada.mega_store.Service.ReclamoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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

}
