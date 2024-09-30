package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.DireccionEnvioDto;

import com.programacion_avanzada.mega_store.Service.IDireccionEnvioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping
public class DireccionEnvioController {
    @Autowired
    IDireccionEnvioService direccionEnvioService;

    @PostMapping("/agregar_direccion/{id}")
    public ResponseEntity<DireccionEnvioDto> agregaDireccionEnvio(@PathVariable("id") Long usuarioId, @RequestBody @Valid DireccionEnvioDto dto) {
        return ResponseEntity.ok(direccionEnvioService.agregaDireccionEnvio(usuarioId, dto));
    }
    
}
