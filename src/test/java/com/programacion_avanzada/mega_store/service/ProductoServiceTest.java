package com.programacion_avanzada.mega_store.service;

import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.Mapper.RegistrarProductoMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;
import com.programacion_avanzada.mega_store.Repository.ProductoRepository;
import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;
import com.programacion_avanzada.mega_store.Service.ProductoService;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
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

    @Test
    void testRegistrarProducto() {
        // Crear un objeto DTO de prueba
        RegistrarProductoDto dto = new RegistrarProductoDto();
        dto.setNombre("Producto de Prueba");
        dto.setDescripcion("Descripción válida");
        dto.setColor("Rojo");
        dto.setTamano("Mediano");
        dto.setStock(100);
        dto.setPrecioUnitario(29.99);
        dto.setMarcaId(1L); // Asegúrate de que coincide con el mock
        dto.setSubCategoriaId(1L); // Asegúrate de que coincide con el mock

        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNombre("MarcaValida");

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setId(1L);
        subCategoria.setNombre("SubCategoriaValida");

        // Configurar el comportamiento del mock para la marca
        when(marcaRepository.existsById(1L)).thenReturn(true);
        when(marcaRepository.findById(1L)).thenReturn(Optional.of(marca));

        // Configurar el comportamiento del mock para la subcategoría
        when(subCategoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.findById(1L)).thenReturn(Optional.of(subCategoria));

        // Verificar si el nombre del producto ya existe
        when(productoRepository.existsByNombre("Producto de Prueba")).thenReturn(false); // Debe devolver false para que pase la verificación

        // Configurar el comportamiento del mock para convertir el DTO a Producto
        Producto productoEsperado = new Producto();
        productoEsperado.setNombre("Producto de Prueba");
        productoEsperado.setDescripcion("Descripción válida");
        productoEsperado.setColor("Rojo");
        productoEsperado.setTamano("Mediano");
        productoEsperado.setStock(100);
        productoEsperado.setPrecioUnitario(29.99);
        productoEsperado.setMarca(marca);
        productoEsperado.setSubcategoria(subCategoria);

        when(registrarProductoMapper.toEntity(dto)).thenReturn(productoEsperado);

        // Simular el comportamiento del repositorio para guardar el producto
        when(productoRepository.save(any(Producto.class))).thenReturn(productoEsperado);
        when(registrarProductoMapper.toDto(productoEsperado)).thenReturn(dto);

        // Ejecutar el metodo a probar
        RegistrarProductoDto result = productoService.registrarProducto(dto);

        // Verificar los resultados
        assertNotNull(result, "El resultado no debería ser nulo");
        assertEquals("Producto de Prueba", result.getNombre());
        assertEquals("Descripción válida", result.getDescripcion());

        // Verificar que se hayan llamado los métodos esperados
        verify(marcaRepository).existsById(1L);
        verify(marcaRepository).findById(1L);
        verify(subCategoriaRepository).existsById(1L);
        verify(subCategoriaRepository).findById(1L);
        verify(productoRepository).existsByNombre("Producto de Prueba");
        verify(productoRepository).save(productoEsperado);
    }







    @Test
    void testRegistrarProductoYaExistente() {
        // Crear un objeto DTO de prueba
        RegistrarProductoDto dto = new RegistrarProductoDto();
        dto.setNombre("ProductoExistente");
        dto.setDescripcion("Descripción existente");
        dto.setColor("Rojo");
        dto.setTamano("Mediano");
        dto.setStock(100);
        dto.setPrecioUnitario(29.99);
        dto.setMarcaId(1L); // Asegúrate de que coincide con el mock
        dto.setSubCategoriaId(1L); // Asegúrate de que coincide con el mock

        // Simular que la categoría existe
        when(marcaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsById(1L)).thenReturn(true);
        // Simular que la subcategoría ya existe en el repositorio
        when(productoRepository.existsByNombre("ProductoExistente")).thenReturn(true);

        // Ejecutar el método a probar y verificar que se lanza la excepción
        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
            productoService.registrarProducto(dto);
        });

        assertEquals("El producto ya existe", exception.getMessage());
    }

}

