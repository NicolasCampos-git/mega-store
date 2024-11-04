package com.programacion_avanzada.mega_store.mapper;

import com.programacion_avanzada.mega_store.DTOs.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.RegistrarSubCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RegistrarSubCategoriaMapperTest {

    private final RegistrarSubCategoriaMapper registrarSubCategoriaMapper = Mappers.getMapper(RegistrarSubCategoriaMapper.class);

    @Test
    void testToEntity() {
        // Crear un objeto DTO de prueba
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("SubCategoriaValida");
        dto.setDescripcion("Descripción válida");

        // Usar el mapper para convertir el DTO en entidad
        SubCategoria subCategoria = registrarSubCategoriaMapper.toEntity(dto);

        // Verificar que la marca no sea nula y que los valores sean correctos
        assertNotNull(subCategoria, "La subcategoria no debería ser nula");
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
        RegistrarSubCategoriaDto dto = registrarSubCategoriaMapper.toDto(subCategoria);

        // Verificar que el DTO no sea nulo y que los valores sean correctos
        assertNotNull(dto, "El DTO no debería ser nulo");
        assertEquals("SubCategoriaValida", dto.getNombre());
        assertEquals("Descripción válida", dto.getDescripcion());
    }
}