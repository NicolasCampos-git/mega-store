package com.programacion_avanzada.TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.programacion_avanzada.mega_store.DTOs.AuthDtos.InicioSesionDTO;
import com.programacion_avanzada.mega_store.DTOs.AuthDtos.RecuperarContrasenaDto;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;
import com.programacion_avanzada.mega_store.Service.SesionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class SesionServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private SesionService sesionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests para iniciarSesion

    @Test
    void iniciarSesion_ConCredencialesValidas_DebeRetornarUsuario() {
        String email = "usuario@dominio.com";
        String password = "pass123";

        InicioSesionDTO dto = new InicioSesionDTO();
        dto.setEmail(email);
        dto.setContrasena(password);

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setContrasena(password);

        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);

        Usuario resultado = sesionService.iniciarSesion(dto);

        assertNotNull(resultado);
        assertEquals(email, resultado.getEmail());
        verify(usuarioRepository).findByEmail(email);
    }

    @Test
    void iniciarSesion_ConEmailNulo_LanzaExcepcion() {
        InicioSesionDTO dto = new InicioSesionDTO();
        dto.setEmail("");
        dto.setContrasena("algo");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            sesionService.iniciarSesion(dto);
        });

        assertEquals("El email no puede estar vacío.", ex.getMessage());
    }

    @Test
    void iniciarSesion_ConEmailInvalido_LanzaExcepcion() {
        InicioSesionDTO dto = new InicioSesionDTO();
        dto.setEmail("emailinvalido");
        dto.setContrasena("algo");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            sesionService.iniciarSesion(dto);
        });

        assertEquals("El email no es válido.", ex.getMessage());
    }

    @Test
    void iniciarSesion_ConUsuarioNoExistente_LanzaExcepcion() {
        String email = "usuario@dominio.com";
        InicioSesionDTO dto = new InicioSesionDTO();
        dto.setEmail(email);
        dto.setContrasena("pass123");

        when(usuarioRepository.findByEmail(email)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            sesionService.iniciarSesion(dto);
        });

        assertEquals("El email o la contraseña son incorrectos.", ex.getMessage());
    }

    @Test
    void iniciarSesion_ConContrasenaIncorrecta_LanzaExcepcion() {
        String email = "usuario@dominio.com";
        InicioSesionDTO dto = new InicioSesionDTO();
        dto.setEmail(email);
        dto.setContrasena("passIncorrecto");

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setContrasena("passCorrecto");

        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            sesionService.iniciarSesion(dto);
        });

        assertEquals("El email o la contraseña son incorrectos.", ex.getMessage());
    }

    // Tests para recuperarContrasena

    @Test
    void recuperarContrasena_ConEmailValido_DebeActualizarContrasena() {
        String email = "usuario@dominio.com";
        RecuperarContrasenaDto dto = new RecuperarContrasenaDto();
        dto.setEmail(email);

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setContrasena("oldPass");

        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = sesionService.recuperarContrasena(dto);

        assertEquals("1234", resultado.getContrasena());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void recuperarContrasena_ConEmailNulo_LanzaExcepcion() {
        RecuperarContrasenaDto dto = new RecuperarContrasenaDto();
        dto.setEmail(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            sesionService.recuperarContrasena(dto);
        });

        assertEquals("El email no puede estar vacío.", ex.getMessage());
    }

    @Test
    void recuperarContrasena_ConEmailInvalido_LanzaExcepcion() {
        RecuperarContrasenaDto dto = new RecuperarContrasenaDto();
        dto.setEmail("emailinvalido");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            sesionService.recuperarContrasena(dto);
        });

        assertEquals("El email no es válido.", ex.getMessage());
    }

    @Test
    void recuperarContrasena_ConUsuarioNoExistente_LanzaExcepcion() {
        String email = "usuario@dominio.com";
        RecuperarContrasenaDto dto = new RecuperarContrasenaDto();
        dto.setEmail(email);

        when(usuarioRepository.findByEmail(email)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            sesionService.recuperarContrasena(dto);
        });

        assertEquals("Credencial invalidad.", ex.getMessage());
    }
}

