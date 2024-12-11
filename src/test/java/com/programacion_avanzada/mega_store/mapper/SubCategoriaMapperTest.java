package com.programacion_avanzada.mega_store.mapper;

import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;
import com.programacion_avanzada.mega_store.Mapper.SubCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SubCategoriaMapperTest {

    private final SubCategoriaMapper subCategoriaMapper = Mappers.getMapper(SubCategoriaMapper.class);

    @Test
    void testToEntity() {
        // Crear un objeto DTO de prueba
        SubCategoriaDTO dto = new SubCategoriaDTO();
        dto.setNombre("SubCategoriaValida");
        dto.setDescripcion("Descripción válida");

        // Usar el mapper para convertir el DTO en entidad
        SubCategoria subCategoria = subCategoriaMapper.toEntity(dto);

        // Verificar que la categoria no sea nula y que los valores sean correctos
        assertNotNull(subCategoria, "La SubCategoria no debería ser nula");
        assertEquals("SubCategoriaValida", subCategoria.getNombre());
        assertEquals("Descripción válida", subCategoria.getDescripcion());
    }

    @Test
    void testToDto() {
        // Crear un objeto de entidad de prueba
        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNombre("SubCategoriaValida");
        subCategoria.setDescripcion("Descripción válida");

        // Usar el mapper para convertir la entidad en DTO
        SubCategoriaDTO dto = subCategoriaMapper.toDto(subCategoria);

        // Verificar que el DTO no sea nulo y que los valores sean correctos
        assertNotNull(dto, "El DTO no debería ser nulo");
        assertEquals("SubCategoriaValida", dto.getNombre());
        assertEquals("Descripción válida", dto.getDescripcion());
    }


}
