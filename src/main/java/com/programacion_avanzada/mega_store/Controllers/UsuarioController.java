
package com.programacion_avanzada.mega_store.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.UsuarioDtos.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDtos.UsuarioDto;
import com.programacion_avanzada.mega_store.Service.Interfaces.IUsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000") 
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
    @GetMapping("/listar")
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

    @PutMapping("/AsignarDireccionComoPrincipal/{idUsuario}-{idDireccion}")
    public ResponseEntity<Void> asignarDireccionComoPrincial(@PathVariable("idUsuario") long idUsuario, @PathVariable("idDireccion") long idDireccion) {
        usuarioService.asignarDireccionComoPrincial(idUsuario, idDireccion);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();  // Retorna 204 No Content

    }
    

    
}
