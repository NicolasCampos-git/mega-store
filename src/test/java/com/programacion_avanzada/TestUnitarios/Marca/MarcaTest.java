package com.programacion_avanzada.TestUnitarios.Marca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programacion_avanzada.mega_store.DTOs.MarcaDtos.RegistrarMarcaDto;
import com.programacion_avanzada.mega_store.Mapper.MarcaMappers.RegistrarMarcaMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;
import com.programacion_avanzada.mega_store.Service.MarcaService;

public class MarcaTest {

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private RegistrarMarcaMapper registrarMarcaMapper;

    @InjectMocks
    private MarcaService marcaService;

    @BeforeEach
    public void setup() {
        // Inicializa los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test para verificar que no se puede registrar una marca con menos de 2 caracteres")
    public void registrarMarcaConNombreDeUnCaracterDeberiaLanzarExcepcion() {
        // Arrange (Preparación)
        // Creamos un DTO con un nombre de marca de un solo carácter
        RegistrarMarcaDto marcaDto = new RegistrarMarcaDto();
        marcaDto.setNombre("A");
        marcaDto.setDescripcion("Descripcion valida");

        // Creamos una entidad Marca que corresponde al DTO
        Marca marca = new Marca();
        marca.setNombre("A");
        marca.setDescripcion("Descripcion valida");

        // Configuramos el comportamiento del mock para que devuelva la entidad cuando se mapee el DTO
        when(registrarMarcaMapper.toEntity(marcaDto)).thenReturn(marca);
        
        // Configuramos el mock para que indique que la marca no existe previamente
        when(marcaRepository.existsByNombre(any())).thenReturn(false);

        // Act & Assert (Actuación y Verificación)
        // Verificamos que se lance la excepción con el mensaje esperado al intentar registrar la marca
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(marcaDto);
        });

        // Verificamos que el mensaje de error sea el esperado
        assertEquals("El nombre de la marca debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }
}
