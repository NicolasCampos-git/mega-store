package com.programacion_avanzada.mega_store.service;

import com.programacion_avanzada.mega_store.DTOs.InicioSesionDTO;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;
import com.programacion_avanzada.mega_store.Service.SesionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SesionServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private SesionService sesionService;

    @Test
    void testIniciarSesionConExito() {
        // Crear el DTO con los datos de inicio de sesión
        InicioSesionDTO inicioSesionDTO = new InicioSesionDTO();
        inicioSesionDTO.setEmail("juan.perez@example.com");
        inicioSesionDTO.setContrasena("Contrasena123");

        // Crear el usuario simulado que se devolverá del repositorio
        Usuario usuario = new Usuario();
        usuario.setEmail("juan.perez@example.com");
        usuario.setContrasena("Contrasena123");

        // Configurar el comportamiento del mock del repositorio
        when(usuarioRepository.findByEmail(inicioSesionDTO.getEmail())).thenReturn(usuario);

        // Ejecutar el método de inicio de sesión
        Usuario result = sesionService.iniciarSesion(inicioSesionDTO);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(inicioSesionDTO.getEmail(), result.getEmail());
        assertEquals(inicioSesionDTO.getContrasena(), result.getContrasena());

        // Verificar que el método del repositorio fue llamado
        verify(usuarioRepository).findByEmail(inicioSesionDTO.getEmail());
    }

    @Test
    void testIniciarSesionConEmailIncorrecto() {
        // Crear el DTO con datos incorrectos
        InicioSesionDTO inicioSesionDTO = new InicioSesionDTO();
        inicioSesionDTO.setEmail("juan.perez@example.com");
        inicioSesionDTO.setContrasena("Contrasena123");

        // Configurar el comportamiento del mock del repositorio para devolver null
        when(usuarioRepository.findByEmail(inicioSesionDTO.getEmail())).thenReturn(null);

        // Ejecutar el método de inicio de sesión y verificar que se lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sesionService.iniciarSesion(inicioSesionDTO);
        });

        // Verificar el mensaje de la excepción
        assertEquals("El email o la contraseña son incorrectos.", exception.getMessage());

        // Verificar que el repositorio fue consultado
        verify(usuarioRepository).findByEmail(inicioSesionDTO.getEmail());
    }

    @Test
    void testIniciarSesionConContrasenaIncorrecta() {
        // Crear el DTO con datos incorrectos
        InicioSesionDTO inicioSesionDTO = new InicioSesionDTO();
        inicioSesionDTO.setEmail("juan.perez@example.com");
        inicioSesionDTO.setContrasena("Contrasena123");

        // Crear el usuario con contrasena diferente
        Usuario usuario = new Usuario();
        usuario.setEmail("juan.perez@example.com");
        usuario.setContrasena("OtraContrasena");

        // Configurar el comportamiento del mock del repositorio
        when(usuarioRepository.findByEmail(inicioSesionDTO.getEmail())).thenReturn(usuario);

        // Ejecutar el método de inicio de sesión y verificar que se lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sesionService.iniciarSesion(inicioSesionDTO);
        });

        // Verificar el mensaje de la excepción
        assertEquals("El email o la contraseña son incorrectos.", exception.getMessage());

        // Verificar que el repositorio fue consultado
        verify(usuarioRepository).findByEmail(inicioSesionDTO.getEmail());
    }

    @Test
    void testRecuperarContrasenaConEmailExistente() {
        // Crear el DTO para la recuperación de contraseñas
        InicioSesionDTO inicioSesionDTO = new InicioSesionDTO();
        inicioSesionDTO.setEmail("juan.perez@example.com");

        // Crear el usuario que existirá en la base de datos
        Usuario usuario = new Usuario();
        usuario.setEmail("juan.perez@example.com");
        usuario.setContrasena("Contrasena123");

        // Configurar el comportamiento del mock del repositorio
        when(usuarioRepository.findByEmail(inicioSesionDTO.getEmail())).thenReturn(usuario);

        // Ejecutar el método de recuperación de contraseña
        Usuario result = sesionService.recuperarContrasena(inicioSesionDTO);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals("1234", result.getContrasena());

        // Verificar que el repositorio fue consultado
        verify(usuarioRepository).findByEmail(inicioSesionDTO.getEmail());
    }

    @Test
    void testRecuperarContrasenaConEmailNoExistente() {
        // Crear el DTO con email que no existe
        InicioSesionDTO inicioSesionDTO = new InicioSesionDTO();
        inicioSesionDTO.setEmail("juan.perez@example.com");

        // Configurar el comportamiento del mock del repositorio para devolver null
        when(usuarioRepository.findByEmail(inicioSesionDTO.getEmail())).thenReturn(null);

        // Ejecutar el método de recuperación de contraseña y verificar que se lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sesionService.recuperarContrasena(inicioSesionDTO);
        });

        // Verificar el mensaje de la excepción
        assertEquals("El email no esta registrado.", exception.getMessage());

        // Verificar que el repositorio fue consultado
        verify(usuarioRepository).findByEmail(inicioSesionDTO.getEmail());
    }

    @Test
    void testValidarEmailInvalido() {
        // Crear el DTO con un email inválido
        InicioSesionDTO inicioSesionDTO = new InicioSesionDTO();
        inicioSesionDTO.setEmail("emailinvalido");
        inicioSesionDTO.setContrasena("Contrasena123");

        // Ejecutar el método de validación y verificar que se lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sesionService.iniciarSesion(inicioSesionDTO);
        });

        // Verificar el mensaje de la excepción
        assertEquals("El email no es válido.", exception.getMessage());
    }
}
