
package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    
    @PostMapping("registrar_usuario")
    public ResponseEntity<RegistroUsuarioDto> registrarUsuarioDto(@Valid  @RequestBody RegistroUsuarioDto dto){
        
        return ResponseEntity.ok(usuarioService.registrarUsuario(dto));
    }

    @PutMapping("/actualizar_datos/{id}")
    public ResponseEntity<UsuarioDto> actualizarInformacionPersonal(@PathVariable("id") long usuarioId, @RequestBody UsuarioDto dto){
        
        
        return ResponseEntity.ok(usuarioService.actualizarInformacionPersonal(usuarioId,dto));
    }

    // Método para buscar un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtenerUsuarioPorId(@PathVariable("id") long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // Método para listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // Método para "eliminar" un usuario (desactivar)
    @PutMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable("id") long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();  // Retorna 204 No Content
    }

    // Método para "reactivar" un usuario (activar)
    @PutMapping("/reactivar/{id}")
    public ResponseEntity<Void> reactivarUsuario(@PathVariable("id") long id) {
        usuarioService.reactivarUsuario(id);
        return ResponseEntity.noContent().build();  // Retorna 204 No Content
    }
    

    
}
