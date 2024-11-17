package com.programacion_avanzada.mega_store.mapper;

import com.programacion_avanzada.mega_store.DTOs.RegistrarCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.RegistrarCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RegistrarCategoriaMapperTest {

    private final RegistrarCategoriaMapper registrarCategoriaMapper = Mappers.getMapper(RegistrarCategoriaMapper.class);

    @Test
    void testToEntity() {
        // Crear un objeto DTO de prueba
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("CategoriaValida");
        dto.setDescripcion("Descripción válida");

        // Usar el mapper para convertir el DTO en entidad
        Categoria categoria = registrarCategoriaMapper.toEntity(dto);

        // Verificar que la marca no sea nula y que los valores sean correctos
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
        RegistrarCategoriaDto dto = registrarCategoriaMapper.toDto(categoria);

        // Verificar que el DTO no sea nulo y que los valores sean correctos
        assertNotNull(dto, "El DTO no debería ser nulo");
        assertEquals("CategoriaValida", dto.getNombre());
        assertEquals("Descripción válida", dto.getDescripcion());
    }
}
