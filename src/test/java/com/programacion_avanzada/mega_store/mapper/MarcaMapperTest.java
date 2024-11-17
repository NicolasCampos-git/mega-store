package com.programacion_avanzada.mega_store.mapper;

import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.Mapper.MarcaMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MarcaMapperTest {

    private final MarcaMapper marcaMapper = Mappers.getMapper(MarcaMapper.class);

    @Test
    void testToEntity() {
        // Crear un objeto DTO de prueba
        MarcaDto dto = new MarcaDto();
        dto.setNombre("MarcaValida");
        dto.setDescripcion("Descripción válida");

        // Usar el mapper para convertir el DTO en entidad
        Marca marca = marcaMapper.toEntity(dto);

        // Verificar que la marca no sea nula y que los valores sean correctos
        assertNotNull(marca, "La marca no debería ser nula");
        assertEquals("MarcaValida", marca.getNombre());
        assertEquals("Descripción válida", marca.getDescripcion());
    }

    @Test
    void testToDto() {
        // Crear un objeto de entidad de prueba
        Marca marca = new Marca();
        marca.setNombre("MarcaValida");
        marca.setDescripcion("Descripción válida");

        // Usar el mapper para convertir la entidad en DTO
        MarcaDto dto = marcaMapper.toDto(marca);

        // Verificar que el DTO no sea nulo y que los valores sean correctos
        assertNotNull(dto, "El DTO no debería ser nulo");
        assertEquals("MarcaValida", dto.getNombre());
        assertEquals("Descripción válida", dto.getDescripcion());
    }
}
