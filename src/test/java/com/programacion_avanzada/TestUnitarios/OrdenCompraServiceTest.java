package com.programacion_avanzada.TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programacion_avanzada.mega_store.Modelos.Estado;
import com.programacion_avanzada.mega_store.Modelos.OrdenCompra;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.EstadoRepository;
import com.programacion_avanzada.mega_store.Repository.ItemOrdenRepository;
import com.programacion_avanzada.mega_store.Repository.OrdenCompraRepository;
import com.programacion_avanzada.mega_store.Repository.ProductoRepository;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;
import com.programacion_avanzada.mega_store.Service.OrdenCompraService;

public class OrdenCompraServiceTest {

    @Mock
    private OrdenCompraRepository ordenCompraRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ItemOrdenRepository itemOrdenRepository;

    @Mock
    private EstadoRepository estadoRepository;

    @InjectMocks
    private OrdenCompraService ordenCompraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBugSobreCompra() {
        // Arrange - Configurar un producto con stock limitado
        Long productoId = 1L;
        Producto producto = new Producto();
        producto.setId(productoId);
        producto.setNombre("AUTO");
        producto.setPrecioUnitario(500.0);
        producto.setStock(1); // Solo hay 1 AUTO en stock
        producto.setDescripcion("Un auto de lujo");

        // Configurar usuarios que quieren comprar
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Juan");
        
        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Pedro");

        // Configurar estado de orden
        Estado estadoPendiente = new Estado();
        estadoPendiente.setNombre("PENDIENTE");

        // Configurar prueba
        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario2));
        when(estadoRepository.findByNombre("PENDIENTE")).thenReturn(Optional.of(estadoPendiente));
        when(ordenCompraRepository.save(any(OrdenCompra.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act - Intentar crear dos órdenes para el mismo producto
        Map<Long, Integer> productos = new HashMap<>();
        productos.put(productoId, 1); // Cada usuario intenta comprar 1 AUTO

        OrdenCompra orden1 = ordenCompraService.crearOrden(1L, productos);
        OrdenCompra orden2 = ordenCompraService.crearOrden(2L, productos);

        // Assert - Aquí está el bug: ambas órdenes se crean exitosamente
        assertNotNull(orden1, "La primera orden debe crearse");
        assertNotNull(orden2, "La segunda orden se crea, esto no deberia pasar porque no hay stock.");
        
        // ¡Bug! Se vendieron 2 unidades cuando solo había 1 en stock
        assertEquals(1, orden1.getItems().get(0).getCantidad(), "Primera orden tiene 1 PS5");
        assertEquals(1, orden2.getItems().get(0).getCantidad(), "Segunda orden también tiene 1 PS5 (¡No debería!)");
        
        // El stock del producto debería estar en -1 ahora (¡Claramente un bug!)
        assertEquals(-1, producto.getStock(), "El stock quedó en números negativos");
    }
}