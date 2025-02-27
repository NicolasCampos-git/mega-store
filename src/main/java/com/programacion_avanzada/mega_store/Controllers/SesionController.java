package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.programacion_avanzada.mega_store.DTOs.AuthDtos.InicioSesionDTO;
import com.programacion_avanzada.mega_store.DTOs.AuthDtos.RecuperarContrasenaDto;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Service.Interfaces.ISesionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/sesiones")
@CrossOrigin(origins = "http://localhost:3000") 
@Tag(name = "Auth", description = "API de autenticacion")
public class SesionController {

    @Autowired
    ISesionService sesionService;

    @Operation(summary = "Iniciar sesion", description = "Permite iniciar sesion con las credenciales de un usuario")
    @PostMapping("/iniciar-sesion")
    public Usuario iniciarSesion(@RequestBody InicioSesionDTO inicioSesionDto) {
        return sesionService.iniciarSesion(inicioSesionDto);
    }

    @Operation(summary= "Recuperar contrasena", description = "Permite recuperar la contrasena de un usuario usando su mail. la constrasena se restablece a 1234")
    @PutMapping("/Recuperar-contrasena")
    public Usuario recuperarContrasena(@RequestBody RecuperarContrasenaDto recuperarContrasenaDto) {
        return sesionService.recuperarContrasena(recuperarContrasenaDto);
    }
    
    
}
