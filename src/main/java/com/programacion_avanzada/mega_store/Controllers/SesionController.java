package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.programacion_avanzada.mega_store.DTOs.InicioSesionDTO;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Service.Interfaces.ISesionService;


@RestController
@RequestMapping("/api/sesiones")
@CrossOrigin(origins = "http://localhost:3000") 
public class SesionController {

    @Autowired
    ISesionService sesionService;

    @PostMapping("/iniciar-sesion")
    public Usuario iniciarSesion(@RequestBody InicioSesionDTO inicioSesionDto) {
        return sesionService.iniciarSesion(inicioSesionDto);
    }

    @PutMapping("/Recuperar-contrasena")
    public Usuario recuperarContrasena(@RequestBody InicioSesionDTO inicioSesionDto) {
        return sesionService.recuperarContrasena(inicioSesionDto);
    }
    
    
}
