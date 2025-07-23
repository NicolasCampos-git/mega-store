package com.programacion_avanzada.mega_store.TestSeguridad;

import com.programacion_avanzada.mega_store.DTOs.MarcaDtos.RegistrarMarcaDto;
import com.programacion_avanzada.mega_store.Mapper.MarcaMappers.MarcaMapper;
import com.programacion_avanzada.mega_store.Mapper.MarcaMappers.RegistrarMarcaMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;
import com.programacion_avanzada.mega_store.Service.MarcaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TestMarcaService {

    @Mock
    private MarcaRepository marcaRepository;
    @Mock
    private RegistrarMarcaMapper registrarMarcaMapper;
    @Mock
    private MarcaMapper marcaMapper;

    @InjectMocks
    private MarcaService marcaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Vulnerabilidad en MarcaService: deberiaLanzarExcepcionCuandoNombreContieneScript")
    void deberiaLanzarExcepcionCuandoNombreContieneScript() {
        // Arrange: DTO con XSS en el nombre
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("<script>alert('xss')</script>");
        dto.setDescripcion("Descripción válida");

        // Simula que el mapper devuelve una entidad con los datos maliciosos
        Marca marcaMaliciosa = new Marca();
        marcaMaliciosa.setNombre(dto.getNombre());
        marcaMaliciosa.setDescripcion(dto.getDescripcion());

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marcaMaliciosa);
        when(marcaRepository.existsByNombre(any())).thenReturn(false);

        // Act & Assert: debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        // Verifica que NO se haya intentado guardar la marca
        verify(marcaRepository, never()).save(any(Marca.class));
        verify(registrarMarcaMapper, never()).toDto(any(Marca.class));
    }

}
