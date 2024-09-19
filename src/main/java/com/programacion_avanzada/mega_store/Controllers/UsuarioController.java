package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;

import com.programacion_avanzada.mega_store.Service.IUsuarioService;



@RestController
@RequestMapping
public class UsuarioController {
    @Autowired
    IUsuarioService usuarioService;

    //Metodo HTTP para registrar usuario.
    @PostMapping("registrar_usuario")
    public RegistroUsuarioDto registrarUsuarioDto(@RequestBody RegistroUsuarioDto dto){
        
        return usuarioService.registrarUsuario(dto);
    }

    
}
