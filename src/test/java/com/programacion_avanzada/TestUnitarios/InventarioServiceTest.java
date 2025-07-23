package com.programacion_avanzada.TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.programacion_avanzada.mega_store.DTOs.InventarioDtos.stockDTO;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Service.InventarioService;
import com.programacion_avanzada.mega_store.Service.Interfaces.IProductoService;

import com.programacion_avanzada.mega_store.Service.SenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

public class InventarioServiceTest {

    @Mock
    private IProductoService productoService;

    @Mock
    private SenderService senderService;

    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void agregarStock_Valido_DebeSumarStock() {
        long idProducto = 1L;
        Producto producto = new Producto();
        producto.setId(idProducto);
        producto.setStock(10);

        stockDTO stockDto = new stockDTO();
        stockDto.setCantidad(5);

        when(productoService.buscarPorId(idProducto)).thenReturn(producto);
        when(productoService.guardar(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Producto resultado = inventarioService.agregarStock(idProducto, stockDto);

        assertEquals(15, resultado.getStock());
        verify(productoService).guardar(any(Producto.class));
    }

    @Test
    void agregarStock_CantidadInvalida_LanzaExcepcion() {
        long idProducto = 1L;
        stockDTO stockDto = new stockDTO();
        stockDto.setCantidad(0); // cantidad inválida

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            inventarioService.agregarStock(idProducto, stockDto);
        });

        assertEquals("La cantidad debe estar entre 1 y 999999.", ex.getMessage());
    }

    @Test
    void agregarStock_SuperaMaximo_LanzaExcepcion() {
        long idProducto = 1L;
        Producto producto = new Producto();
        producto.setId(idProducto);
        producto.setStock(999995);

        stockDTO stockDto = new stockDTO();
        stockDto.setCantidad(10);

        when(productoService.buscarPorId(idProducto)).thenReturn(producto);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            inventarioService.agregarStock(idProducto, stockDto);
        });

        assertEquals("El stock no puede ser mayor a 999999.", ex.getMessage());
    }

    @Test
    void quitarStock_Valido_DebeRestarStock() {
        long idProducto = 1L;
        Producto producto = new Producto();
        producto.setId(idProducto);
        producto.setStock(10);
        producto.setUmbralBajoStock(5);

        stockDTO stockDto = new stockDTO();
        stockDto.setCantidad(3);

        when(productoService.buscarPorId(idProducto)).thenReturn(producto);
        when(productoService.guardar(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Producto resultado = inventarioService.quitarStock(idProducto, stockDto);

        assertEquals(7, resultado.getStock());
        verify(productoService).guardar(any(Producto.class));
        verify(senderService, never()).notificarBajoStock(anyLong());
    }

    @Test
    void quitarStock_StockBajo_DeberiaNotificar() {
        long idProducto = 1L;
        Producto producto = new Producto();
        producto.setId(idProducto);
        producto.setStock(6);
        producto.setUmbralBajoStock(5);

        stockDTO stockDto = new stockDTO();
        stockDto.setCantidad(2); // queda stock 4 < umbral 5

        when(productoService.buscarPorId(idProducto)).thenReturn(producto);
        when(productoService.guardar(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Producto resultado = inventarioService.quitarStock(idProducto, stockDto);

        assertEquals(4, resultado.getStock());
        verify(senderService).notificarBajoStock(idProducto);
        verify(productoService).guardar(any(Producto.class));
    }

    @Test
    void quitarStock_CantidadInvalida_LanzaExcepcion() {
        long idProducto = 1L;
        Producto producto = new Producto();
        producto.setId(idProducto);
        producto.setStock(10);

        stockDTO stockDto = new stockDTO();
        stockDto.setCantidad(0);

        when(productoService.buscarPorId(idProducto)).thenReturn(producto);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            inventarioService.quitarStock(idProducto, stockDto);
        });

        assertEquals("La cantidad debe estar entre 1 y el maximo stock.", ex.getMessage());
    }

    @Test
    void quitarStock_CantidadMayorAlStock_LanzaExcepcion() {
        long idProducto = 1L;
        Producto producto = new Producto();
        producto.setId(idProducto);
        producto.setStock(5);

        stockDTO stockDto = new stockDTO();
        stockDto.setCantidad(10);

        when(productoService.buscarPorId(idProducto)).thenReturn(producto);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            inventarioService.quitarStock(idProducto, stockDto);
        });

        assertEquals("La cantidad debe estar entre 1 y el maximo stock.", ex.getMessage());
    }

    @Test
    void revisarStock_NotificaProductosConStockBajo() {
        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setStock(3);
        producto1.setUmbralBajoStock(5);

        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setStock(10);
        producto2.setUmbralBajoStock(5);

        List<Producto> productos = Arrays.asList(producto1, producto2);

        when(productoService.listar()).thenReturn(productos);

        inventarioService.revisarStock();

        verify(senderService).notificarBajoStock(1L);
        verify(senderService, never()).notificarBajoStock(2L);
    }
}

