package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.programacion_avanzada.mega_store.DTOs.MarcaDtos.MarcaDto;
import com.programacion_avanzada.mega_store.DTOs.MarcaDtos.RegistrarMarcaDto;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Service.Interfaces.IMarcaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/marcas")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Marca", description = "API de marcas")
public class MarcaController {

    @Autowired
    private IMarcaService marcaService;

    @Operation(summary = "Registrar una marca", description = "Permite registrar una marca en la base de datos")
    @PostMapping("/registrar")
    public RegistrarMarcaDto registrarMarca(@RequestBody @Valid RegistrarMarcaDto dto) {
        return marcaService.registrarMarca(dto);
    }

    @Operation(summary = "Listar marcas", description = "Permite listar todas las marcas registradas en la base de datos")
    @GetMapping("/listar")
    public List<MarcaDto> listarMarcas() {
        return marcaService.listar();
    }

    @Operation(summary = "Buscar marca por ID", description = "Permite buscar una marca por su ID")
    @GetMapping("/{id}")
    public Marca buscarPorId(@PathVariable long id) {
        return marcaService.buscarPorId(id);
    }

    @Operation(summary = "Eliminar marca por ID", description = "Permite eliminar una marca por su ID de forma logica")
    @DeleteMapping("/eliminar/{id}")
    public void eliminarMarca(@PathVariable long id) {
        marcaService.eliminar(id);
    }

    @Operation(summary = "Actualizar marca", description = "Permite actualizar una marca por su ID")
    @PutMapping("/actualizar/{id}")
    public MarcaDto actualizar(@PathVariable long id, @RequestBody @Valid MarcaDto dto) {
        return marcaService.actualizar(id, dto);
    }

    @Operation(summary = "Reactivar marca", description = "Permite reactivar una marca por su ID")
    @PutMapping("/reactivar/{id}")
    public MarcaDto reactivar(@PathVariable long id) {
        return marcaService.reactivar(id);
    }
}
