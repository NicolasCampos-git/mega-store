package com.programacion_avanzada.mega_store.service;


import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;
import com.programacion_avanzada.mega_store.Service.ISenderService;
import com.programacion_avanzada.mega_store.Service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ISenderService senderService;

    @InjectMocks
    private UsuarioService usuarioService;


    @Test
    void testRegistrarUsuario() {
        // Crear el objeto DTO para el test
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Pérez");
        dto.setTelefono("123456789");
        dto.setEmail("juan.perez@example.com");
        dto.setContrasena("Contrasena1");
        dto.setContrasenaRepetida("Contrasena1");

        // Crear el usuario esperado después del mapeo
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setTelefono("123456789");
        usuario.setEmail("juan.perez@example.com");
        usuario.setContrasena("Contrasena1");

        // Configurar el comportamiento del mock
        when(usuarioRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Ejecutar el metodo
        RegistroUsuarioDto result = usuarioService.registrarUsuario(dto);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(dto.getNombre(), result.getNombre());
        assertEquals(dto.getApellido(), result.getApellido());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getTelefono(), result.getTelefono());

        // Verificar que los métodos del repositorio y el servicio de envío de correos fueron llamados
        verify(usuarioRepository).existsByEmail(dto.getEmail());
        verify(usuarioRepository).save(any(Usuario.class));
        verify(senderService).enviarCorreo(dto.getEmail());
    }

    @Test
    void testRegistrarUsuarioConContrasenasNoCoinciden() {
        // Crear el objeto DTO para el test
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Ana");
        dto.setApellido("López");
        dto.setTelefono("987654321");
        dto.setEmail("ana.lopez@example.com");
        dto.setContrasena("Contrasena1");
        dto.setContrasenaRepetida("OtraContrasena");

        // Ejecutar y verificar que se lance la excepción esperada
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.registrarUsuario(dto)
        );

        assertEquals("Las contrasenas no coinciden", exception.getMessage());

        // Verificar que el metodo existsByEmail nunca fue llamado
        verify(usuarioRepository, never()).existsByEmail(anyString());
        // Verificar que no se llame al metodo save ni al servicio de envío de correos
        verify(usuarioRepository, never()).save(any());
        verify(senderService, never()).enviarCorreo(anyString());
    }

    @Test
    void testRegistrarUsuarioConCorreoYaRegistrado() {
        // Crear el objeto DTO para el test
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Pérez");
        dto.setTelefono("123456789");
        dto.setEmail("juan.perez@example.com");
        dto.setContrasena("Contrasena1");
        dto.setContrasenaRepetida("Contrasena1");

        // Simular que el correo ya existe en la base de datos
        when(usuarioRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        // Ejecutar y verificar que se lance la excepción esperada
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.registrarUsuario(dto)
        );

        assertEquals("El email ya está registrado.", exception.getMessage());

        // Verificar que el método existsByEmail fue llamado
        verify(usuarioRepository).existsByEmail(dto.getEmail());
        // Verificar que no se llame al método save ni al servicio de envío de correos
        verify(usuarioRepository, never()).save(any());
        verify(senderService, never()).enviarCorreo(anyString());
    }
}
