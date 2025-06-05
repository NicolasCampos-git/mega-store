package com.programacion_avanzada.TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programacion_avanzada.mega_store.DTOs.ReclamoDtos.RegistrarReclamoDto;
import com.programacion_avanzada.mega_store.Modelos.Estado;
import com.programacion_avanzada.mega_store.Modelos.OrdenCompra;
import com.programacion_avanzada.mega_store.Modelos.Reclamo;
import com.programacion_avanzada.mega_store.Modelos.TipoReclamo;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.EstadoRepository;
import com.programacion_avanzada.mega_store.Repository.ReclamoRepository;
import com.programacion_avanzada.mega_store.Repository.TipoReclamoRepositoty;
import com.programacion_avanzada.mega_store.Service.OrdenCompraService;
import com.programacion_avanzada.mega_store.Service.ReclamoService;
import com.programacion_avanzada.mega_store.Service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class TestReclamo {

    @Mock
    private ReclamoRepository reclamoRepository;
    
    @Mock
    private UsuarioService usuarioService;
    
    @Mock
    private OrdenCompraService ordenCompraService;
    
    @Mock
    private TipoReclamoRepositoty tipoReclamoRepository;
    
    @Mock
    private EstadoRepository estadoRepository;
    
    @InjectMocks
    private ReclamoService reclamoService;
    
    private Usuario usuario;
    private OrdenCompra ordenCompra;
    private TipoReclamo tipoReclamo;
    private Estado estado;
    private RegistrarReclamoDto reclamoDto;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        usuario = new Usuario();
        usuario.setId(1L);
        
        ordenCompra = new OrdenCompra();
        ordenCompra.setId(1L);
        ordenCompra.setUsuario(usuario);
        
        tipoReclamo = new TipoReclamo();
        tipoReclamo.setId(1L);
        
        estado = new Estado();
        estado.setId(1L);
        estado.setNombre("Registrado");
        
        // Configurar DTO
        reclamoDto = new RegistrarReclamoDto();
        reclamoDto.setMotivo("Producto defectuoso");
        reclamoDto.setDescripcion("El producto llegó dañado");
        reclamoDto.setIdTipoReclamo(1L);
        reclamoDto.setIdUsuario(1L);
        reclamoDto.setIdOrdenCompra(1L);
        
        // Usar lenient() para los mocks que podrían no usarse en todos los tests
        lenient().when(usuarioService.buscarPorId(anyLong())).thenReturn(usuario);
        lenient().when(ordenCompraService.obtenerOrdenPorId(anyLong())).thenReturn(ordenCompra);
        lenient().when(tipoReclamoRepository.findById(anyLong())).thenReturn(tipoReclamo);
        lenient().when(estadoRepository.findByNombre("Registrado")).thenReturn(java.util.Optional.of(estado));
    }

    @Test
    void testBugLongitudMotivoIncorrecta() {
        reclamoDto.setMotivo("M");
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.registrarReclamo(reclamoDto);
        });
        
        assertEquals("El motivo debe tener entre 2 y 50 caracteres", exception.getMessage());
    }

    @Test
    void testBugValidacionMotivo() {
        when(reclamoRepository.findById(1L)).thenReturn(null);
        reclamoDto.setMotivo("Motivo123");
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.registrarReclamo(reclamoDto);
        });
        
        assertEquals("El motivo solo debe tener letras y espacios", exception.getMessage());
    }

    @Test
    void testBugActualizacionEstadoIncorrecto() {
        Reclamo reclamo = new Reclamo();
        reclamo.setId(1L);
        reclamo.setEstado(estado);
        when(reclamoRepository.findById(1L)).thenReturn(reclamo);
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.resolverReclamo(1L);
        });
        
        assertEquals("Cambio de estado no valido", exception.getMessage());
    }

    @Test
    void testBugValidacionDescripcionLongitud() {
        reclamoDto.setDescripcion("A");
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.registrarReclamo(reclamoDto);
        });
        
        assertEquals("La descripcion debe tener entre 2 y 255 caracteres", exception.getMessage());
    }

    @Test
    void testBugReclamoNulo() {
        reclamoDto.setMotivo(null);
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.registrarReclamo(reclamoDto);
        });
        
        assertEquals("Motivo no puede estar vacio", exception.getMessage());
    }
}