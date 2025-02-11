package com.programacion_avanzada.mega_store.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.DireccionEnvioDto;
import com.programacion_avanzada.mega_store.Modelos.DireccionEnvio;
import com.programacion_avanzada.mega_store.Service.Interfaces.IDireccionEnvioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;






@RestController
@RequestMapping("/api/direcciones")
public class DireccionEnvioController {
    @Autowired
    IDireccionEnvioService direccionEnvioService;

    @PostMapping("/agregar_direccion/{id}")
    public ResponseEntity<DireccionEnvioDto> agregaDireccionEnvio(@PathVariable("id") Long usuarioId, @RequestBody @Valid DireccionEnvioDto dto) {
        return ResponseEntity.ok(direccionEnvioService.agregaDireccionEnvio(usuarioId, dto));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DireccionEnvio>> listarDireccionesEnvio() {
        return ResponseEntity.ok(direccionEnvioService.listar());
    }

    @GetMapping("/buscar_direccion/{id}")
    public ResponseEntity<DireccionEnvio> buscarDireccionEnvio(@PathVariable("id") Long id) {
        return ResponseEntity.ok(direccionEnvioService.buscarDireccionEnvio(id));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<DireccionEnvio> actualizarDireccionEnvio(@PathVariable("id") Long id, @RequestBody @Valid DireccionEnvioDto dto) {
        return ResponseEntity.ok(direccionEnvioService.actualizarDireccionEnvio(id, dto));
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarDireccionEnvio(@PathVariable("id") Long id) {
        direccionEnvioService.eliminarDireccionEnvio(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reactivar/{id}")
    public ResponseEntity<DireccionEnvio> reactivar(@PathVariable("id") Long id) {
        
        return ResponseEntity.ok(direccionEnvioService.reactivarDireccionEnvio(id));
        
    }
    
    
   
    



    
    
}
