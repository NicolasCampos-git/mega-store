import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

package com.programacion_avanzada.TestUnitarios;

package com.programacion_avanzada.TestUnitarios;



public class OrdenServiceTest {

    @Mock
    private OrdenRepository ordenRepository;

    @InjectMocks
    private OrdenService ordenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearOrden() {
        Orden orden = new Orden();
        orden.setId(1L);
        orden.setDescripcion("Nueva Orden");

        when(ordenRepository.save(any(Orden.class))).thenReturn(orden);

        Orden resultado = ordenService.crearOrden(orden);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Nueva Orden", resultado.getDescripcion());
        verify(ordenRepository, times(1)).save(orden);
    }

    @Test
    void testObtenerOrdenPorId() {
        Orden orden = new Orden();
        orden.setId(1L);
        orden.setDescripcion("Orden Existente");

        when(ordenRepository.findById(1L)).thenReturn(java.util.Optional.of(orden));

        Orden resultado = ordenService.obtenerOrdenPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Orden Existente", resultado.getDescripcion());
        verify(ordenRepository, times(1)).findById(1L);
    }

    @Test
    void testEliminarOrden() {
        Long ordenId = 1L;

        doNothing().when(ordenRepository).deleteById(ordenId);

        ordenService.eliminarOrden(ordenId);

        verify(ordenRepository, times(1)).deleteById(ordenId);
    }
}
