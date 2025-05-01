package com.programacion_avanzada.TestIntegracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
public class ReclamoIntegracionTest {

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
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        
        // Crear datos de prueba
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan@example.com");
        
        ordenCompra = new OrdenCompra();
        ordenCompra.setId(1L);
        ordenCompra.setUsuario(usuario);
        
        tipoReclamo = new TipoReclamo();
        tipoReclamo.setId(1L);
        tipoReclamo.setNombre("Producto dañado");
        
        estado = new Estado();
        estado.setId(1L);
        estado.setNombre("Registrado");
        
        // Configurar comportamiento de los mocks
        when(usuarioService.buscarPorId(anyLong())).thenReturn(usuario);
        when(usuarioService.validarIdUsuario(anyLong())).thenReturn(true);
        
        when(ordenCompraService.obtenerOrdenPorId(anyLong())).thenReturn(ordenCompra);
        when(ordenCompraService.existeOrdenCompra(anyLong())).thenReturn(true);
        
        when(tipoReclamoRepository.findById(anyLong())).thenReturn(tipoReclamo);
        when(tipoReclamoRepository.existsById(anyLong())).thenReturn(true);
        
        when(estadoRepository.findByNombre("Registrado")).thenReturn(java.util.Optional.of(estado));
        
        // Configurar el comportamiento para el guardado de un reclamo
        when(reclamoRepository.save(any(Reclamo.class))).thenAnswer(invocation -> {
            Reclamo reclamo = invocation.getArgument(0);
            if (reclamo.getId() == 0) {
                reclamo.setId(1L); // Simular la asignación de ID por la base de datos
            }
            return reclamo;
        });
    }
    
    @Test
    public void testRegistrarReclamo() {
        // Arrange - Preparar los datos necesarios para el test
        RegistrarReclamoDto reclamoDto = new RegistrarReclamoDto();
        // Cambiamos "Producto dañado" por "Producto defectuoso" para evitar la ñ
        reclamoDto.setMotivo("Producto defectuoso");
        reclamoDto.setDescripcion("El producto llego con rayones en la superficie");
        reclamoDto.setIdUsuario(1L);
        reclamoDto.setIdOrdenCompra(1L);
        reclamoDto.setIdTipoReclamo(1L);
        
        // Act - Ejecutar el método que se está probando
        Reclamo reclamoRegistrado = reclamoService.registrarReclamo(reclamoDto);
        
        // Assert - Verificar que el resultado sea el esperado
        assertNotNull(reclamoRegistrado, "El reclamo registrado no debe ser nulo");
        assertNotNull(reclamoRegistrado.getId(), "El reclamo debe tener un ID asignado");
        assertEquals(reclamoDto.getMotivo().toLowerCase(), reclamoRegistrado.getMotivo(), 
                    "El motivo debe coincidir (convertido a minúsculas)");
        assertEquals(reclamoDto.getDescripcion().toLowerCase(), reclamoRegistrado.getDescripcion(), 
                    "La descripción debe coincidir (convertida a minúsculas)");
        assertEquals(1L, reclamoRegistrado.getUsuario().getId(), 
                    "El ID de usuario debe coincidir");
        assertEquals(1L, reclamoRegistrado.getOrdenCompra().getId(), 
                    "El ID de la orden de compra debe coincidir");
        assertEquals(1L, reclamoRegistrado.getTipoReclamo().getId(), 
                    "El ID del tipo de reclamo debe coincidir");
        assertEquals("Registrado", reclamoRegistrado.getEstado().getNombre(), 
                    "El estado inicial debe ser 'Registrado'");
        assertNotNull(reclamoRegistrado.getFechaCreacion(), "Debe tener fecha de creación");
    }
}