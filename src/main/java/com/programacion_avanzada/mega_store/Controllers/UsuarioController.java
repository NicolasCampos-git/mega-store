package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDto;
import com.programacion_avanzada.mega_store.Service.IUsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping
public class UsuarioController {
    @Autowired
    IUsuarioService usuarioService;

    //Metodo HTTP para registrar usuario.
    @PostMapping("registrar_usuario")
    public ResponseEntity<RegistroUsuarioDto> registrarUsuarioDto(@Valid  @RequestBody RegistroUsuarioDto dto){
        
        return ResponseEntity.ok(usuarioService.registrarUsuario(dto));
    }

    @PutMapping("/actualizar_datos/{id}")
    public ResponseEntity<UsuarioDto> actualizarInformacionPersonal(@PathVariable("id") long usuarioId, @RequestBody UsuarioDto dto){
        
        
        return ResponseEntity.ok(usuarioService.actualizarInformacionPersonal(usuarioId,dto));
    }
    

    
}
