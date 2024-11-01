package com.programacion_avanzada.mega_store.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.programacion_avanzada.mega_store.DTOs.RegistrarMarcaDto;
import com.programacion_avanzada.mega_store.Mapper.RegistrarMarcaMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegistrarMarcaMapperTest {

    private final RegistrarMarcaMapper registrarMarcaMapper = Mappers.getMapper(RegistrarMarcaMapper.class);

    @Test
    void testToEntity() {
        // Crear un objeto DTO de prueba
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("MarcaValida");
        dto.setDescripcion("Descripción válida");

        // Usar el mapper para convertir el DTO en entidad
        Marca marca = registrarMarcaMapper.toEntity(dto);

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
        RegistrarMarcaDto dto = registrarMarcaMapper.toDto(marca);

        // Verificar que el DTO no sea nulo y que los valores sean correctos
        assertNotNull(dto, "El DTO no debería ser nulo");
        assertEquals("MarcaValida", dto.getNombre());
        assertEquals("Descripción válida", dto.getDescripcion());
    }
}
