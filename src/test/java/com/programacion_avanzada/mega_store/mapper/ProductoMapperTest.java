package com.programacion_avanzada.mega_store.mapper;

import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.Mapper.ProductoMapper;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductoMapperTest {

    private final ProductoMapper productoMapper = Mappers.getMapper(ProductoMapper.class);

    @Test
    void testToEntity() {
        // Crear un objeto DTO de prueba
        RegistrarProductoDto dto = new RegistrarProductoDto();
        dto.setNombre("NombreValido");
        dto.setDescripcion("Descripcion valida");
        dto.setTamano("Grande");
        dto.setColor("Azul");
        dto.setPrecioUnitario(1.0);
        dto.setStock(1);
        dto.setUmbralBajoStock(1);

        // Usar el mapper para convertir el DTO en entidad
        Producto producto = productoMapper.toEntity(dto);

        // Verificar que la categoria no sea nula y que los valores sean correctos
        assertNotNull(producto, "La categoria no debería ser nula");
        assertEquals("NombreValido", producto.getNombre());
        assertEquals("Descripcion valida", producto.getDescripcion());
        assertEquals("Grande", producto.getTamano());
        assertEquals("Azul", producto.getColor());
        assertEquals(1.0, producto.getPrecioUnitario());
        assertEquals(1, producto.getStock());
        assertEquals(1, producto.getUmbralBajoStock());

    }

    @Test
    void testToDto() {
        // Crear un objeto de entidad de prueba
        Producto producto = new Producto();
        producto.setNombre("NombreValido");
        producto.setDescripcion("Descripcion valida");
        producto.setTamano("Grande");
        producto.setColor("Azul");
        producto.setPrecioUnitario(1.0);
        producto.setStock(1);
        producto.setUmbralBajoStock(1);

        // Usar el mapper para convertir la entidad en DTO
        ProductoDto dto = productoMapper.toDto(producto);

        // Verificar que el DTO no sea nulo y que los valores sean correctos
        assertNotNull(dto, "El DTO no debería ser nulo");
        assertEquals("NombreValido", producto.getNombre());
        assertEquals("Descripcion valida", producto.getDescripcion());
        assertEquals("Grande", producto.getTamano());
        assertEquals("Azul", producto.getColor());
        assertEquals(1.0, producto.getPrecioUnitario());
        assertEquals(1, producto.getStock());
        assertEquals(1, producto.getUmbralBajoStock());
    }


}
