package com.programacion_avanzada.TestUnitarios;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.programacion_avanzada.mega_store.DTOs.DireccionDtos.DireccionEnvioDto;
import com.programacion_avanzada.mega_store.Modelos.DireccionEnvio;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.DireccionEnvioRepository;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;
import com.programacion_avanzada.mega_store.Service.DireccionEnvioService;
import com.programacion_avanzada.mega_store.Mapper.DireccionMappers.DireccionEnvioMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

public class DireccionEnvioServiceTest {

    @Mock
    private DireccionEnvioRepository direccionEnvioRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DireccionEnvioService direccionEnvioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void agregaDireccionEnvio_ConDatosValidos_DebeRegistrarExitosamente() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        DireccionEnvio entity = DireccionEnvioMapper.toEntity(dto);
        Usuario usuario = new Usuario();
        entity.setUsuario(usuario);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(direccionEnvioRepository.save(any(DireccionEnvio.class))).thenReturn(entity);

        DireccionEnvioDto resultado = direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);

        assertNotNull(resultado);
        assertEquals("Cordoba", resultado.getProvincia());
        verify(direccionEnvioRepository).save(any(DireccionEnvio.class));
    }

    @Test
    void agregaDireccionEnvio_ConUsuarioInexistente_DebeLanzarExcepcion() {
        long usuarioId = 999L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("Usuario no encontrado", ex.getMessage());
    }

    @Test
    void actualizarDireccionEnvio_ConDireccionExistente_DebeActualizar() {
        long direccionId = 1L;
        DireccionEnvio direccionExistente = new DireccionEnvio();
        direccionExistente.setId(direccionId);
        direccionExistente.setProvincia("Mendoza");

        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        when(direccionEnvioRepository.findById(direccionId)).thenReturn(Optional.of(direccionExistente));
        when(direccionEnvioRepository.save(any(DireccionEnvio.class))).thenReturn(direccionExistente);

        DireccionEnvio actualizado = direccionEnvioService.actualizarDireccionEnvio(direccionId, dto);

        assertEquals("Cordoba", actualizado.getProvincia());
    }

    @Test
    void actualizarDireccionEnvio_DireccionNoExiste_DebeLanzarExcepcion() {
        long direccionId = 999L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");

        when(direccionEnvioRepository.findById(direccionId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                direccionEnvioService.actualizarDireccionEnvio(direccionId, dto)
        );

        assertEquals("La dirección de envío no existe.", ex.getMessage());
    }

    @Test
    void eliminarDireccionEnvio_ConDireccionExistente_DebeDesactivarse() {
        long direccionId = 1L;
        DireccionEnvio direccion = new DireccionEnvio();
        direccion.setId(direccionId);
        direccion.setEstaActivo(true);

        when(direccionEnvioRepository.findById(direccionId)).thenReturn(Optional.of(direccion));

        direccionEnvioService.eliminarDireccionEnvio(direccionId);

        assertFalse(direccion.isEstaActivo());
        verify(direccionEnvioRepository).save(direccion);
    }

    @Test
    void eliminarDireccionEnvio_DireccionNoExiste_DebeLanzarExcepcion() {
        long direccionId = 999L;
        when(direccionEnvioRepository.findById(direccionId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                direccionEnvioService.eliminarDireccionEnvio(direccionId)
        );

        assertEquals("La dirección de envío no existe.", ex.getMessage());
    }

    @Test
    void listarDireccionEnvioPorUsuario_UsuarioValido_DebeRetornarLista() {
        long usuarioId = 1L;
        List<DireccionEnvio> direcciones = List.of(new DireccionEnvio(), new DireccionEnvio());

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(new Usuario()));
        when(direccionEnvioRepository.findByUsuarioId(usuarioId)).thenReturn(direcciones);

        List<DireccionEnvio> resultado = direccionEnvioService.listarDireccionEnvioPorUsuario(usuarioId);

        assertEquals(2, resultado.size());
    }

    @Test
    void listarDireccionEnvioPorUsuario_UsuarioInexistente_DebeLanzarExcepcion() {
        long usuarioId = 999L;
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> direccionEnvioService.listarDireccionEnvioPorUsuario(usuarioId));
    }

    @Test
    void agregaDireccionEnvio_ConProvinciaNula_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La provincia no puede estar vacía.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConProvinciaDeLongitudMenor_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("a");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La provincia debe tener entre 2 y 30 caracteres.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConProvinciaDeLongitudMayor_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Provincia Autonoma del Alto Parana");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La provincia debe tener entre 2 y 30 caracteres.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConProvinciaDeCaracteresEspeciales_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Córdoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La provincia debe contener solo letras y espacios.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCiudadNula_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("");
        dto.setCalle("Bolivar");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La ciudad no puede estar vacía.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCiudadDeLongitudMenor_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("a");
        dto.setCalle("Bolivar");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La ciudad debe tener entre 2 y 30 caracteres.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCiudadDeLongitudMayor_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("San Fernando del Valle de Catamarca");
        dto.setCalle("Bolivar");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La ciudad debe tener entre 2 y 30 caracteres.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCiudadDeCaracteresEspeciales_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa María");
        dto.setCalle("Bolivar");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La ciudad debe contener solo letras y espacios.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCalleNula_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La calle no puede estar vacía.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCalleDeLongitudMenor_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("a");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La calle debe tener entre 2 y 30 caracteres.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCalleDeLongitudMayor_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Avenida de los Libertadores de America");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La calle debe tener entre 2 y 30 caracteres.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCalleDeCaracteresEspeciales_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Santa Lucía");
        dto.setAltura("123");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La calle debe contener solo letras, espacios, apóstrofes y guiones.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConAlturaNula_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La altura no puede estar vacía.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConAlturaDeLongitudMenor_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("1");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La altura debe tener entre 2 y 4 caracteres.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConAlturaDeLongitudMayor_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("12345");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La altura debe tener entre 2 y 4 caracteres.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConAlturaConCaracteresDistintosANumeros_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("12a");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La altura debe contener solo números.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConAlturaConEspacios_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("1 2");
        dto.setCodigoPostal("5900");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("La altura debe contener solo números.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCodigoPostalNulo_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("14");
        dto.setCodigoPostal("");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("El código postal no puede estar vacío.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCodigoPostalDeLongitudMenor_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("14");
        dto.setCodigoPostal("1");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("El código postal debe tener entre 2 y 4 caracteres.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCodigoPostalDeLongitudMayor_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("14");
        dto.setCodigoPostal("12345");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("El código postal debe tener entre 2 y 4 caracteres.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCodigoPostalDeCaracteresDistintosANumeros_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("14");
        dto.setCodigoPostal("590a");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("El código postal debe contener solo números.", ex.getMessage());
    }

    @Test
    void agregaDireccionEnvio_ConCodigoPostalConEspacios_DebeLanzarExcepcion() {
        long usuarioId = 1L;
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia("Cordoba");
        dto.setCiudad("Villa Maria");
        dto.setCalle("Bolivar");
        dto.setAltura("14");
        dto.setCodigoPostal("5 90");
        dto.setDescripcionDireccionEnvio("Casa");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            direccionEnvioService.agregaDireccionEnvio(usuarioId, dto);
        });

        assertEquals("El código postal debe contener solo números.", ex.getMessage());
    }
}
