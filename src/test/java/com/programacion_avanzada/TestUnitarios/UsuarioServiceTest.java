package com.programacion_avanzada.TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import com.programacion_avanzada.mega_store.Mapper.CategoriaMappers.RegistrarCategoriaMapper;
import com.programacion_avanzada.mega_store.Mapper.UsuarioMappers.RegistroUsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programacion_avanzada.mega_store.DTOs.UsuarioDtos.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDtos.UsuarioDto;
import com.programacion_avanzada.mega_store.Modelos.DireccionEnvio;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.DireccionEnvioRepository;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;
import com.programacion_avanzada.mega_store.Service.Interfaces.ISenderService;
import com.programacion_avanzada.mega_store.Service.UsuarioService;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private DireccionEnvioRepository direccionRepository;

    @Mock
    private ISenderService senderService;

    @Mock
    private RegistroUsuarioMapper registrarUsuarioMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarUsuario_ConDatosValidos_DebeRegistrarCorrectamente() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("juan");
        dto.setApellido("perez");
        dto.setEmail("juan@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        Usuario usuario = new Usuario();
        usuario.setNombre("juan");
        usuario.setApellido("perez");
        usuario.setEmail("juan@test.com");
        usuario.setTelefono("123456789");
        usuario.setContrasena("Password1");
        usuario.setEstaActivo(true);
        usuario.setRol("cliente");

        when(usuarioRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.registrarUsuario(dto);

        assertNotNull(resultado);
        assertEquals("juan", resultado.getNombre());
        assertEquals("perez", resultado.getApellido());
        assertEquals("juan@test.com", resultado.getEmail());
        assertTrue(resultado.isEstaActivo());
        assertEquals("cliente", resultado.getRol());
        verify(senderService).enviarCorreo("juan@test.com");
    }

    @Test
    void actualizarInformacionPersonal_UsuarioExistente_DebeActualizar() {
        long id = 1L;

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Carlos");
        dto.setApellido("Lopez");
        dto.setEmail("carlos@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");

        Usuario usuario = new Usuario();
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario actualizado = usuarioService.actualizarInformacionPersonal(id, dto);

        assertNotNull(actualizado);
        assertEquals("Carlos", actualizado.getNombre());
        assertEquals("Lopez", actualizado.getApellido());
        assertEquals("carlos@test.com", actualizado.getEmail());
    }

    @Test
    void buscarPorId_UsuarioExistente_DebeRetornarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Maria");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Maria", resultado.getNombre());
    }

    @Test
    void eliminarUsuario_UsuarioExistente_DebeDesactivarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEstaActivo(true);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.eliminarUsuario(1L);

        assertFalse(usuario.isEstaActivo());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void reactivarUsuario_UsuarioExistente_DebeActivarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEstaActivo(false);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.reactivarUsuario(1L);

        assertTrue(usuario.isEstaActivo());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void asignarDireccionComoPrincipal_CambiaPrincipalCorrectamente() {
        DireccionEnvio dir1 = new DireccionEnvio();
        dir1.setId(1L);
        dir1.setEsPrincipal(false);

        DireccionEnvio dir2 = new DireccionEnvio();
        dir2.setId(2L);
        dir2.setEsPrincipal(true);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setDirecciones(Arrays.asList(dir1, dir2));
        dir1.setUsuario(usuario);
        dir2.setUsuario(usuario);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.asignarDireccionComoPrincial(1L, 1L);

        assertTrue(dir1.isEsPrincipal());
        assertFalse(dir2.isEsPrincipal());
        assertEquals(usuario, resultado);
    }

    @Test
    void registrarUsuario_ConNombreVacio_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El nombre no puede estar vacio.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConNombreDeLongitudMenor_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("a");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El nombre debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConNombreDeLongitudMayor_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("EsteEsUnNombreDeUsuarioExcesivamenteLargoQueSuperaLos64CaracteresPermitidosFacilmente");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El nombre debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConNombreConEspacios_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Ju an");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El nombre no debe contener espacios.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConApellidoVacio_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El apellido no puede estar vacio.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConApellidoDeLongitudMenor_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("a");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El apellido debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConApellidoDeLongitudMayor_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("EsteEsUnApellidoDeUsuarioExcesivamenteLargoQueSuperaLos64CaracteresPermitidosFacilmente");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El apellido debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConApellidoConEspacios_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Pe rez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El apellido no debe contener espacios.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConTelefonoVacio_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El telefono no puede estar vacio.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConTelefonoDeLongitudMenor_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("12345678");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El telefono debe tener entre 9 y 15 caracteres.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConTelefonoDeLongitudMayor_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("1234567891234567");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El telefono debe tener entre 9 y 15 caracteres.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConTelefonoConCaracteresDistintosANumeros_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123a456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El telefono solo debe contener numeros.", exception.getMessage());
    }


    @Test
    void registrarUsuario_ConTelefonoConEspacios_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123 456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El telefono solo debe contener numeros.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConEmailVacio_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El email no puede estar vacío.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConEmailInvalido_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("juanperez");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El email no tiene un formato válido.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConEmailConEspacios_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("perez @test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El email no puede contener espacios.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConEmailDeLongitudMenor_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("@");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El email no tiene un formato válido.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConEmailDeLongitudMayor_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("usuario.de.prueba.con.nombre.muy.largo.para.superar.64.caracteres@example.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("El email debe tener menos de 64 caracteres.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConContrasenaVacia_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("");
        dto.setContrasenaRepetida("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("La contrasena no puede estar vacia.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConContrasenaDeLongitudMenor_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("P123456");
        dto.setContrasenaRepetida("P123456");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("La contrasena debe tener al menos 8 caracteres.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConContrasenaSinNumero_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password");
        dto.setContrasenaRepetida("Password");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("La contrasena debe tener al menos una mayuscula y un numero.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConContrasenaSinMayuscula_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("password1");
        dto.setContrasenaRepetida("password1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("La contrasena debe tener al menos una mayuscula y un numero.", exception.getMessage());
    }

    @Test
    void registrarUsuario_ConContrasenasNoCoincidentes_DebeLanzarExcepcion() {
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEmail("perez@test.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Password1");
        dto.setContrasenaRepetida("Password2");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("Las contrasenas no coinciden.", exception.getMessage());
    }


}

