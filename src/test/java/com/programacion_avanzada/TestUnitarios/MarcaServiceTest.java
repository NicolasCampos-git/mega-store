package com.programacion_avanzada.TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDtos.RegistrarCategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programacion_avanzada.mega_store.DTOs.MarcaDtos.MarcaDto;
import com.programacion_avanzada.mega_store.DTOs.MarcaDtos.RegistrarMarcaDto;
import com.programacion_avanzada.mega_store.Mapper.MarcaMappers.MarcaMapper;
import com.programacion_avanzada.mega_store.Mapper.MarcaMappers.RegistrarMarcaMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;

public class MarcaServiceTest {

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private RegistrarMarcaMapper registrarMarcaMapper;

    @Mock
    private MarcaMapper marcaMapper;

    @InjectMocks
    private com.programacion_avanzada.mega_store.Service.MarcaService marcaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarMarca_ConNombreMinimo_DebeRegistrarExitosamente() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("vw"); // Nombre con longitud mínima válida (2 caracteres)
        dto.setDescripcion("Marca de vehiculos alemana");

        Marca marca = new Marca();
        marca.setNombre("vw");
        marca.setDescripcion("Marca de vehiculos alemana");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);
        when(marcaRepository.existsByNombre(any())).thenReturn(false);
        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);
        when(registrarMarcaMapper.toDto(any(Marca.class))).thenReturn(dto);

        // Act
        RegistrarMarcaDto resultado = marcaService.registrarMarca(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("vw", resultado.getNombre());
        assertEquals("Marca de vehiculos alemana", resultado.getDescripcion());
    }

    @Test
    void registrarMarca_ConNombreMaximo_DebeRegistrarExitosamente() {
        // Arrange
        String nombreLongitudMaxima = "ab"; // Empezamos con 2 caracteres para validar
        while (nombreLongitudMaxima.length() < 64) {
            nombreLongitudMaxima += "a";
        }
        
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre(nombreLongitudMaxima);
        dto.setDescripcion("Marca de vehiculos con nombre de longitud maxima");

        Marca marca = new Marca();
        marca.setNombre(nombreLongitudMaxima);
        marca.setDescripcion("Marca de vehiculos con nombre de longitud maxima");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);
        when(marcaRepository.existsByNombre(any())).thenReturn(false);
        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);
        when(registrarMarcaMapper.toDto(any(Marca.class))).thenReturn(dto);

        // Act
        RegistrarMarcaDto resultado = marcaService.registrarMarca(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(nombreLongitudMaxima, resultado.getNombre());
        assertEquals(64, resultado.getNombre().length(), "El nombre debe tener exactamente 64 caracteres");
        assertEquals("Marca de vehiculos con nombre de longitud maxima", resultado.getDescripcion());
    }

    @Test
    void registrarMarca_ConNombreMenorAlMinimo_DebeLanzarExcepcion() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("a"); // Nombre con longitud menor al mínimo permitido (1 caracter)
        dto.setDescripcion("Marca de vehiculos");

        Marca marca = new Marca();
        marca.setNombre("a");
        marca.setDescripcion("Marca de vehiculos");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("El nombre de la marca debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    void registrarMarca_ConNombreMayorAlMaximo_DebeLanzarExcepcion() {
        // Arrange
        String nombreLongitudExcesiva = "a".repeat(65); // Creamos un nombre con 65 caracteres
        
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre(nombreLongitudExcesiva);
        dto.setDescripcion("Marca de vehiculos");

        Marca marca = new Marca();
        marca.setNombre(nombreLongitudExcesiva);
        marca.setDescripcion("Marca de vehiculos");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("El nombre de la marca debe tener entre 2 y 64 caracteres.", exception.getMessage());
        assertEquals(65, nombreLongitudExcesiva.length(), "El nombre debe tener 65 caracteres para validar que excede el máximo");
    }

    @Test
    void registrarMarca_ConNombreSoloLetras_DebeRegistrarExitosamente() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("ferrari"); // Nombre válido con solo letras
        dto.setDescripcion("Marca de vehiculos deportivos italiana");

        Marca marca = new Marca();
        marca.setNombre("ferrari");
        marca.setDescripcion("Marca de vehiculos deportivos italiana");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);
        when(marcaRepository.existsByNombre(any())).thenReturn(false);
        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);
        when(registrarMarcaMapper.toDto(any(Marca.class))).thenReturn(dto);

        // Act
        RegistrarMarcaDto resultado = marcaService.registrarMarca(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("ferrari", resultado.getNombre());
        assertTrue(resultado.getNombre().matches("^[a-zA-Z]+$"), "El nombre debe contener solo letras");
        assertTrue(resultado.getNombre().length() >= 2 && resultado.getNombre().length() <= 64, 
                  "El nombre debe tener entre 2 y 64 caracteres");
        assertEquals("Marca de vehiculos deportivos italiana", resultado.getDescripcion());
    }

    @Test
    void registrarMarca_ConEspaciosEnMedio_DebeLanzarExcepcion() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("land rover"); // Nombre válido en longitud pero con espacio en medio
        dto.setDescripcion("Marca de vehiculos todoterreno britanica");

        Marca marca = new Marca();
        marca.setNombre("land rover");
        marca.setDescripcion("Marca de vehiculos todoterreno britanica");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("El nombre de la marca no debe contener espacios.", exception.getMessage());
        assertTrue(dto.getNombre().contains(" "), "El nombre debe contener un espacio");
        assertTrue(dto.getNombre().length() >= 2 && dto.getNombre().length() <= 64, 
                  "El nombre debe tener entre 2 y 64 caracteres");
    }

    @Test
    void registrarMarca_ConCaracteresEspeciales_DebeLanzarExcepcion() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("alfa@#$"); // Nombre con caracteres especiales
        dto.setDescripcion("Marca de vehiculos deportivos italiana");

        Marca marca = new Marca();
        marca.setNombre("alfa@#$");
        marca.setDescripcion("Marca de vehiculos deportivos italiana");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("El nombre de la marca no debe contener caracteres especiales.", exception.getMessage());
        assertFalse(dto.getNombre().matches("^[a-zA-Z]+$"), "El nombre no debe contener solo letras");
    }

    @Test
    void registrarMarca_ConNumerosEnNombre_DebeLanzarExcepcion() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("ferrari123"); // Nombre con números
        dto.setDescripcion("Marca de vehiculos deportivos italiana");

        Marca marca = new Marca();
        marca.setNombre("ferrari123");
        marca.setDescripcion("Marca de vehiculos deportivos italiana");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("El nombre no debe contener números.", exception.getMessage());
        assertTrue(dto.getNombre().matches(".*\\d.*"), "El nombre debe contener números");
    }

    @Test
    void registrarMarca_ConNombreVacio_DebeLanzarExcepcion() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre(""); // Nombre vacío
        dto.setDescripcion("Marca de vehiculos");

        Marca marca = new Marca();
        marca.setNombre("");
        marca.setDescripcion("Marca de vehiculos");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("El nombre de la marca no puede estar vacío.", exception.getMessage());
    }


    @Test
    void registrarMarca_ConNombreDuplicado_DebeLanzarExcepcion() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("toyota"); // Nombre que ya existe
        dto.setDescripcion("Marca de vehiculos japonesa");

        Marca marca = new Marca();
        marca.setNombre("toyota");
        marca.setDescripcion("Marca de vehiculos japonesa");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);
        when(marcaRepository.existsByNombre(marca.getNombre().trim())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("El nombre de la marca ya está registrado", exception.getMessage());
        verify(marcaRepository).existsByNombre(marca.getNombre().trim());
    }

    @Test
    void registrarMarca_ConDescripcionVacia_DebeLanzarExcepcion() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("toyota"); // Nombre válido
        dto.setDescripcion(""); // Descripción vacía

        Marca marca = new Marca();
        marca.setNombre("toyota");
        marca.setDescripcion("");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("La descripción no puede estar vacía", exception.getMessage());
    }

    @Test
    void registrarMarca_ConDescripcionLongitudMinima_DebeRegistrarExitosamente() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("toyota"); // Nombre válido
        dto.setDescripcion("ok"); // Descripción con longitud mínima (2 caracteres)

        Marca marca = new Marca();
        marca.setNombre("toyota");
        marca.setDescripcion("ok");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);
        when(marcaRepository.existsByNombre(any())).thenReturn(false);
        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);
        when(registrarMarcaMapper.toDto(any(Marca.class))).thenReturn(dto);

        // Act
        RegistrarMarcaDto resultado = marcaService.registrarMarca(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("toyota", resultado.getNombre());
        assertEquals("ok", resultado.getDescripcion());
        assertEquals(2, resultado.getDescripcion().length(), "La descripción debe tener exactamente 2 caracteres");
    }

    @Test
    void registrarMarca_ConDescripcionLongitudMenor_DebeLanzarExcepcion() {
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("electronica");
        dto.setDescripcion("a");

        Marca marca = new Marca();
        marca.setNombre("electronica");
        marca.setDescripcion("a");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("La descripción debe tener entre 2 y 64 caracteres", exception.getMessage());
    }

    @Test
    void registrarMarca_ConDescripcionLongitudMayor_DebeLanzarExcepcion() {
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("electronica");
        dto.setDescripcion("Esta es una descripción de ejemplo pensada para superar los 64 caracteres y así poder probar límites de validación o almacenamiento en bases de datos, archivos u otros sistemas.");

        Marca marca = new Marca();
        marca.setNombre("electronica");
        marca.setDescripcion("Esta es una descripción de ejemplo pensada para superar los 64 caracteres y así poder probar límites de validación o almacenamiento en bases de datos, archivos u otros sistemas.");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("La descripción debe tener entre 2 y 64 caracteres", exception.getMessage());
    }

    @Test
    void registrarMarca_ConDescripcionConNumeros_DebeLanzarExcepcion() {
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("electronica");
        dto.setDescripcion("123");

        Marca marca = new Marca();
        marca.setNombre("electronica");
        marca.setDescripcion("123");

        when(registrarMarcaMapper.toEntity(any(RegistrarMarcaDto.class))).thenReturn(marca);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("La descripción no debe contener números", exception.getMessage());
    }
}
