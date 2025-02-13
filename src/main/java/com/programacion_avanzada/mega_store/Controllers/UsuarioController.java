
package com.programacion_avanzada.mega_store.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.UsuarioDtos.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDtos.UsuarioDto;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Service.Interfaces.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Usuario", description = "API de Usuario")
public class UsuarioController {
    
    @Autowired
    IUsuarioService usuarioService;

    @Operation(summary = "Registrar un usuario", description = "Este método permite registrar un usuario en la base de datos.")
    @PostMapping("registrar_usuario")
    public ResponseEntity<Usuario> registrarUsuarioDto(@Valid  @RequestBody RegistroUsuarioDto dto){
        
        return ResponseEntity.ok(usuarioService.registrarUsuario(dto));
    }

    @Operation(summary = "Actualizar información personal", description = "Este método permite actualizar la información personal de un usuario.")
    @PatchMapping("/actualizar_datos/{id}")
    public ResponseEntity<Usuario> actualizarInformacionPersonal(@PathVariable("id") long usuarioId, @RequestBody UsuarioDto dto){
        
        
        return ResponseEntity.ok(usuarioService.actualizarInformacionPersonal(usuarioId,dto));
    }

    @Operation(summary = "Obtener un usuario por ID", description = "Este método permite obtener un usuario por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable("id") long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @Operation(summary = "Listar usuarios", description = "Este método permite listar todos los usuarios.")
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @Operation(summary = "Eliminar un usuario", description = "Este método permite eliminar un usuario de forma logica( soft delete ).")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable("id") long id) {
        try{
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();  // Retorna 204 No Content
        }catch(Exception e){
            return ResponseEntity.notFound().build();  // Retorna 404 Not Found
        }
    }

    @Operation(summary = "Reactivar un usuario", description = "Este método permite reactivar un usuario.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario reactivado correctamente."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    })
    @PutMapping("/reactivar/{id}")
    public ResponseEntity<Void> reactivarUsuario(@PathVariable("id") long id) {
        try {
            usuarioService.reactivarUsuario(id);
            return ResponseEntity.noContent().build();  // Retorna 204 No Content
        } catch (Exception e) {
            return ResponseEntity.notFound().build();  // Retorna 404 Not Found
        }
    }

    @Operation(summary = "Asignar dirección como principal", description = "Este método permite asignar una dirección como principal. Se debe enviar el ID del usuario y el ID de la dirección.")
    @PutMapping("/AsignarDireccionComoPrincipal/{idUsuario}-{idDireccion}")
    public ResponseEntity<Void> asignarDireccionComoPrincial(@PathVariable("idUsuario") long idUsuario, @PathVariable("idDireccion") long idDireccion) {
        try {
            usuarioService.asignarDireccionComoPrincial(idUsuario, idDireccion);
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();  // Retorna 200 OK
        } catch (Exception e) {
            return ResponseEntity.notFound().build();  // Retorna 404 Not Found
        }
    }  
    

    
}
