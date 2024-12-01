package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.InicioSesionDTO;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Service.ISesionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/sesiones")
@CrossOrigin(origins = "http://localhost:3000") 
public class SesionController {

    @Autowired
    ISesionService sesionService;

    @GetMapping("/iniciar-sesion")
    public Usuario iniciarSesion(@RequestBody InicioSesionDTO inicioSesionDto) {
        return sesionService.iniciarSesion(inicioSesionDto);
    }

    @PutMapping("/Recuperar-contrasena")
    public Usuario recuperarContrasena(@RequestBody InicioSesionDTO inicioSesionDto) {
        return sesionService.recuperarContrasena(inicioSesionDto);
    }
    
    
}
