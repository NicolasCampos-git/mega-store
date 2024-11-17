package com.programacion_avanzada.mega_store.mapper;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.CategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CategoriaMapperTest {

    private final CategoriaMapper categoriaMapper = Mappers.getMapper(CategoriaMapper.class);

    @Test
    void testToEntity() {
        // Crear un objeto DTO de prueba
        CategoriaDto dto = new CategoriaDto();
        dto.setNombre("CategoriaValida");
        dto.setDescripcion("Descripción válida");

        // Usar el mapper para convertir el DTO en entidad
        Categoria categoria = categoriaMapper.toEntity(dto);

        // Verificar que la categoria no sea nula y que los valores sean correctos
        assertNotNull(categoria, "La categoria no debería ser nula");
        assertEquals("CategoriaValida", categoria.getNombre());
        assertEquals("Descripción válida", categoria.getDescripcion());
    }

    @Test
    void testToDto() {
        // Crear un objeto de entidad de prueba
        Categoria categoria = new Categoria();
        categoria.setNombre("CategoriaValida");
        categoria.setDescripcion("Descripción válida");

        // Usar el mapper para convertir la entidad en DTO
        CategoriaDto dto = categoriaMapper.toDto(categoria);

        // Verificar que el DTO no sea nulo y que los valores sean correctos
        assertNotNull(dto, "El DTO no debería ser nulo");
        assertEquals("CategoriaValida", dto.getNombre());
        assertEquals("Descripción válida", dto.getDescripcion());
    }


}
