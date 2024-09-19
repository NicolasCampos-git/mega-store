package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.DireccionEnvioDto;

import com.programacion_avanzada.mega_store.Service.IDireccionEnvioService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping
public class DireccionEnvioController {
    @Autowired
    IDireccionEnvioService direccionEnvioService;

    @PostMapping("/agregar_direccion/{id}")
    public DireccionEnvioDto agregaDireccionEnvio(@PathVariable("id") Long usuarioId, @RequestBody DireccionEnvioDto dto) {
        return direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
    }
    
}
