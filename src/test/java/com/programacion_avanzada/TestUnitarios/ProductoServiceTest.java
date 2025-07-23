package com.programacion_avanzada.TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.programacion_avanzada.mega_store.DTOs.ProductoDtos.ProductoDto;
import com.programacion_avanzada.mega_store.DTOs.ProductoDtos.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.Mapper.ProductoMappers.ProductoMapper;
import com.programacion_avanzada.mega_store.Mapper.ProductoMappers.RegistrarProductoMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;
import com.programacion_avanzada.mega_store.Repository.ProductoRepository;
import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;
import com.programacion_avanzada.mega_store.Service.ProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @InjectMocks
    private ProductoService productoService;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private SubCategoriaRepository subCategoriaRepository;

    @Mock
    private RegistrarProductoMapper registrarProductoMapper;

    private ProductoDto productoDto;

    @BeforeEach
    public void setUp() {
        productoDto = new ProductoDto();
        // No es necesario crear manualmente la instancia de categoriaService,
        // ya que Mockito se encarga de ello con @InjectMocks
    }

    @Test
    void registrarProducto_ConDatosValidos_DebeRegistrarExitosamente() {
        RegistrarProductoDto dto = new RegistrarProductoDto();
        dto.setNombre("Laptop");
        dto.setDescripcion("Laptop de alta gama");
        dto.setTamano("15 pulgadas");
        dto.setColor("Negro");
        dto.setPrecioUnitario(1500.0);
        dto.setStock(10);
        dto.setUmbralBajoStock(2);
        dto.setMarcaId(1L);
        dto.setSubCategoriaId(1L);

        Producto producto = new Producto();
        producto.setNombre("Laptop");
        producto.setDescripcion("Laptop de alta gama");
        producto.setTamano("15 pulgadas");
        producto.setColor("Negro");
        producto.setPrecioUnitario(1500.0);
        producto.setStock(10);
        producto.setUmbralBajoStock(2);
        producto.setMarca(new Marca());
        producto.setSubcategoria(new SubCategoria());

        when(marcaRepository.existsById(dto.getMarcaId())).thenReturn(true);
        when(subCategoriaRepository.existsById(dto.getSubCategoriaId())).thenReturn(true);
        when(productoRepository.existsByNombre(dto.getNombre())).thenReturn(false);
        when(registrarProductoMapper.toEntity(any(RegistrarProductoDto.class))).thenReturn(producto);
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.registrarProducto(dto);

        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombre());
        assertEquals("Laptop de alta gama", resultado.getDescripcion());
        assertEquals("15 pulgadas", resultado.getTamano());
        assertEquals("Negro", resultado.getColor());
        assertEquals(1500.0, resultado.getPrecioUnitario());
        assertEquals(10, resultado.getStock());
        assertEquals(2, resultado.getUmbralBajoStock());
    }

    @Test
    void registrarProducto_ConNombreDuplicado_DebeLanzarExcepcion() {
        RegistrarProductoDto dto = new RegistrarProductoDto();
        dto.setNombre("Laptop");
        dto.setDescripcion("Laptop de alta gama");
        dto.setTamano("15 pulgadas");
        dto.setColor("Negro");
        dto.setPrecioUnitario(1500.0);
        dto.setStock(10);
        dto.setUmbralBajoStock(2);
        dto.setMarcaId(1L);
        dto.setSubCategoriaId(1L);

        when(marcaRepository.existsById(dto.getMarcaId())).thenReturn(true);
        when(subCategoriaRepository.existsById(dto.getSubCategoriaId())).thenReturn(true);
        when(productoRepository.existsByNombre(dto.getNombre())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.registrarProducto(dto);
        });

        assertEquals("El producto ya existe", exception.getMessage());
        verify(productoRepository).existsByNombre(dto.getNombre());
    }

    @Test
    void registrarProducto_ConNombreVacio_DebeLanzarExcepcion() {
        RegistrarProductoDto dto = new RegistrarProductoDto();
        dto.setNombre("");
        dto.setDescripcion("Producto sin nombre");
        dto.setTamano("15 pulgadas");
        dto.setColor("Negro");
        dto.setPrecioUnitario(100.0);
        dto.setStock(5);
        dto.setUmbralBajoStock(1);
        dto.setMarcaId(1L);
        dto.setSubCategoriaId(1L);

        when(marcaRepository.existsById(dto.getMarcaId())).thenReturn(true);
        when(subCategoriaRepository.existsById(dto.getSubCategoriaId())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.registrarProducto(dto);
        });

        assertEquals("El nombre del producto no puede estar vacío.", exception.getMessage());
    }

    @Test
    void registrarProducto_ConPrecioNegativo_DebeLanzarExcepcion() {
        RegistrarProductoDto dto = new RegistrarProductoDto();
        dto.setNombre("Laptop");
        dto.setDescripcion("Laptop de alta gama");
        dto.setTamano("15 pulgadas");
        dto.setColor("Negro");
        dto.setPrecioUnitario(-100.0);
        dto.setStock(5);
        dto.setUmbralBajoStock(1);
        dto.setMarcaId(1L);
        dto.setSubCategoriaId(1L);

        when(marcaRepository.existsById(dto.getMarcaId())).thenReturn(true);
        when(subCategoriaRepository.existsById(dto.getSubCategoriaId())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.registrarProducto(dto);
        });

        assertEquals("El precio unitario del producto debe ser mayor a 0.", exception.getMessage());
    }

    @Test
    void registrarProducto_ConDescripcionVacia_DebeLanzarExcepcion() {
        RegistrarProductoDto dto = new RegistrarProductoDto();
        dto.setNombre("Laptop");
        dto.setDescripcion("");
        dto.setTamano("15 pulgadas");
        dto.setColor("Negro");
        dto.setPrecioUnitario(1500.0);
        dto.setStock(5);
        dto.setUmbralBajoStock(1);
        dto.setMarcaId(1L);
        dto.setSubCategoriaId(1L);

        when(marcaRepository.existsById(dto.getMarcaId())).thenReturn(true);
        when(subCategoriaRepository.existsById(dto.getSubCategoriaId())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.registrarProducto(dto);
        });

        assertEquals("La descripcion del producto no puede estar vacía.", exception.getMessage());
    }
}

