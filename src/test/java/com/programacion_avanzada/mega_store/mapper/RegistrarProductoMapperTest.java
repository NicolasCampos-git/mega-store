package com.programacion_avanzada.mega_store.mapper;

import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;
import com.programacion_avanzada.mega_store.Mapper.RegistrarProductoMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RegistrarProductoMapperTest {

    private final RegistrarProductoMapper registrarProductoMapper = new RegistrarProductoMapper();

    @Test
    void testToEntity() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

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
        Producto producto = registrarProductoMapper.toEntity(dto);

        // Verificar que la marca no sea nula y que los valores sean correctos
        assertNotNull(producto, "El producto no debería ser nula");
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
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNombre("MarcaValida");

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setId(1L);
        subCategoria.setNombre("SubCategoriaValida");

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
        RegistrarProductoDto dto = registrarProductoMapper.toDto(producto);

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
