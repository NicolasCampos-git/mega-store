package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarMarcaDto;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Service.Interfaces.IMarcaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/marcas")
@CrossOrigin(origins = "http://localhost:3000")
public class MarcaController {

    @Autowired
    private IMarcaService marcaService;

    // Endpoint para registrar una nueva marca
    @PostMapping("/registrar")
    public RegistrarMarcaDto registrarMarca(@RequestBody @Valid RegistrarMarcaDto dto) {
        return marcaService.registrarMarca(dto);
    }

    // Endpoint para listar todas las marcas
    @GetMapping("/listar")
    public List<MarcaDto> listarMarcas() {
        return marcaService.listar();
    }

    // Endpoint para buscar una marca por su ID
    @GetMapping("/{id}")
    public Marca buscarPorId(@PathVariable long id) {
        return marcaService.buscarPorId(id);
    }

    // Endpoint para eliminar una marca por su ID
    @DeleteMapping("/eliminar/{id}")
    public void eliminarMarca(@PathVariable long id) {
        marcaService.eliminar(id);
    }

    // Endpoint para actualizar una marca por su ID
    @PutMapping("/actualizar/{id}")
    public MarcaDto actualizar(@PathVariable long id, @RequestBody @Valid MarcaDto dto) {
        return marcaService.actualizar(id, dto);
    }

    // Endpoint para reactivar una marca eliminada
    @PutMapping("/reactivar/{id}")
    public MarcaDto reactivar(@PathVariable long id) {
        return marcaService.reactivar(id);
    }
}
