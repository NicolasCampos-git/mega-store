package com.programacion_avanzada.mega_store.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.DireccionDtos.DireccionEnvioDto;
import com.programacion_avanzada.mega_store.Modelos.DireccionEnvio;
import com.programacion_avanzada.mega_store.Service.Interfaces.IDireccionEnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;








@RestController
@RequestMapping("/api/direcciones")
@Tag(name = "Direccion de envio", description = "API de direcciones de envio")
public class DireccionEnvioController {
    
    @Autowired
    IDireccionEnvioService direccionEnvioService;

    @Operation(summary = "Agregar direccion de envio", description = "Permite agregar una direccion de envio a un usuario con su ID")
    @PostMapping("/agregar_direccion/{id}")
    public ResponseEntity<DireccionEnvioDto> agregaDireccionEnvio(@PathVariable("id") Long usuarioId, @RequestBody @Valid DireccionEnvioDto dto) {
        return ResponseEntity.ok(direccionEnvioService.agregaDireccionEnvio(usuarioId, dto));
    }

    @Operation(summary = "Listar direcciones de envio", description = "Permite listar todas las direcciones de envio registradas en la base de datos")
    @GetMapping("/listar")
    public ResponseEntity<List<DireccionEnvio>> listarDireccionesEnvio() {
        return ResponseEntity.ok(direccionEnvioService.listar());
    }

    @Operation(summary = "Buscar direccion de envio por ID", description = "Permite buscar una direccion de envio por su ID")
    @GetMapping("/buscar_direccion/{id}")
    public ResponseEntity<DireccionEnvio> buscarDireccionEnvio(@PathVariable("id") Long id) {
        return ResponseEntity.ok(direccionEnvioService.buscarDireccionEnvio(id));
    }

    @Operation(summary = "Actualizar direccion de envio", description = "Permite actualizar una direccion de envio por su ID")
    @PutMapping("/editar/{id}")
    public ResponseEntity<DireccionEnvio> actualizarDireccionEnvio(@PathVariable("id") Long id, @RequestBody @Valid DireccionEnvioDto dto) {
        return ResponseEntity.ok(direccionEnvioService.actualizarDireccionEnvio(id, dto));
    }


    @Operation(summary = "Eliminar direccion de envio por ID", description = "Permite eliminar una direccion de envio por su ID de forma logica")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarDireccionEnvio(@PathVariable("id") Long id) {
        direccionEnvioService.eliminarDireccionEnvio(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reactivar direccion de envio", description = "Permite reactivar una direccion de envio por su ID")
    @PutMapping("/reactivar/{id}")
    public ResponseEntity<DireccionEnvio> reactivar(@PathVariable("id") Long id) {
        
        return ResponseEntity.ok(direccionEnvioService.reactivarDireccionEnvio(id));
        
    }

    @Operation(summary = "Listar direcciones de envio por usuario", description = "Permite listar todas las direcciones de envio registradas en la base de datos asoadas a un usuario")
    @GetMapping("/Listas/{usuarioId}")
    public ResponseEntity<List<DireccionEnvio>> listarDireccionEnvioPorUsuario(@PathVariable("usuarioId") Long usuarioId) {
        return ResponseEntity.ok(direccionEnvioService.listarDireccionEnvioPorUsuario(usuarioId));
    }
    
    
    
    
   
    



    
    
}
