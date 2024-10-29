package com.programacion_avanzada.mega_store.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.programacion_avanzada.mega_store.DTOs.RegistrarMarcaDto;
import com.programacion_avanzada.mega_store.Mapper.RegistrarMarcaMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;
import com.programacion_avanzada.mega_store.Service.MarcaService;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MarcaServiceTest {

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private RegistrarMarcaMapper registrarMarcaMapper;

    @InjectMocks
    private MarcaService marcaService;

    @BeforeEach
    public void setUp() {
        // Aquí puedes realizar configuraciones previas si es necesario
    }

    @Test
    public void testNombreValido() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("MarcaValida");
        dto.setDescripcion("Descripción válida");

        Marca marca = new Marca();
        marca.setNombre(dto.getNombre());
        marca.setDescripcion(dto.getDescripcion());

        when(marcaRepository.existsByNombre(dto.getNombre().trim())).thenReturn(false);
        when(registrarMarcaMapper.toEntity(dto)).thenReturn(marca);
        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);
        when(registrarMarcaMapper.toDto(marca)).thenReturn(dto);

        // Act
        RegistrarMarcaDto resultado = marcaService.registrarMarca(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("MarcaValida", resultado.getNombre());
    }

    @Test
    void testRegistrarMarca() {
        // Crear un objeto DTO de prueba
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("MarcaValida");
        dto.setDescripcion("Descripción válida");

        // Configurar el comportamiento del mock para que devuelva una nueva Marca
        Marca marcaEsperada = new Marca();
        marcaEsperada.setNombre("MarcaValida");
        marcaEsperada.setDescripcion("Descripción válida");
        when(registrarMarcaMapper.toEntity(dto)).thenReturn(marcaEsperada);

        // Ahora llama al método después de haber configurado el mock
        Marca marca = registrarMarcaMapper.toEntity(dto);

        // Simular el comportamiento del repositorio
        when(marcaRepository.existsByNombre(anyString())).thenReturn(false);
        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);
        when(registrarMarcaMapper.toDto(marca)).thenReturn(dto); // Supongamos que también quieres devolver el DTO.

        // Ejecutar el método a probar
        RegistrarMarcaDto result = marcaService.registrarMarca(dto);

        // Verificar los resultados
        assertNotNull(result, "El resultado no debería ser nulo");
        assertEquals("MarcaValida", result.getNombre());
        assertEquals("Descripción válida", result.getDescripcion());

        // Verificar que se hayan llamado los métodos esperados
        verify(marcaRepository).existsByNombre("MarcaValida");
        verify(marcaRepository).save(marca);
    }

    @Test
    void testRegistrarMarcaYaExistente() {
        // Crear un objeto DTO de prueba
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("MarcaExistente");
        dto.setDescripcion("Descripción existente");

        // Configurar el comportamiento del mock para que devuelva una nueva Marca
        Marca marcaEsperada = new Marca();
        marcaEsperada.setNombre("MarcaValida");
        marcaEsperada.setDescripcion("Descripción válida");
        when(registrarMarcaMapper.toEntity(dto)).thenReturn(marcaEsperada);

        // Simular que la marca ya existe en el repositorio
        when(marcaRepository.existsByNombre(anyString())).thenReturn(true);

        // Ejecutar el método a probar y verificar que se lanza la excepción
        Exception exception = assertThrows(EntityExistsException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("La marca ya existe", exception.getMessage());
    }

    @Test
    void testRegistrarMarcaConNombreCorto() {
        // Crear un objeto DTO de prueba
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("A");
        dto.setDescripcion("Descripción existente");

        // Verificar que se lance una excepción al intentar registrar la marca
        assertThrows(EntityExistsException.class, () -> {
            marcaService.registrarMarca(dto);
        }, "Se esperaba que se lanzara una excepción al registrar una marca con un nombre de un solo carácter");
    }


    @Test
    void testRegistrarMarcaConNombreLargo() {
        // Crear un objeto DTO de prueba con un nombre de más de 64 caracteres
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("A".repeat(65)); // Genera un string de 65 caracteres 'A'
        dto.setDescripcion("Descripción válida");

        // Verificar que se lance una excepción al intentar registrar la marca
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        assertEquals("El nombre de la marca no puede tener más de 64 caracteres", exception.getMessage());
    }


    @Test
    public void testNombreSinEspaciosEnBlanco() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("Marca Invalida");  // Nombre con espacio en blanco
        dto.setDescripcion("Descripción válida");

        // Aquí no se necesita configurar el mock para el mapeo, ya que se espera que falle antes de llegar a esa parte.

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        // Verificar que el mensaje de la excepción es el esperado
        assertEquals("El nombre no puede contener espacios en blanco", exception.getMessage());
    }

    @Test
    public void testNombreSinCaracteresNumericos() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("Marca123");  // Nombre con caracteres numéricos
        dto.setDescripcion("Descripción válida");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        // Verificar que el mensaje de la excepción es el esperado
        assertEquals("El nombre no puede contener caracteres numéricos", exception.getMessage());
    }

    @Test
    void testRegistrarMarcaConNombreValidoSinDescripcion() {
        // Crear un objeto DTO de prueba solo con el nombre
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("MarcaValida");

        // Configurar el comportamiento del mock para que devuelva una nueva Marca
        Marca marcaEsperada = new Marca();
        marcaEsperada.setNombre("MarcaValida");
        when(registrarMarcaMapper.toEntity(dto)).thenReturn(marcaEsperada);

        // Simular el comportamiento del repositorio
        when(marcaRepository.existsByNombre(anyString())).thenReturn(false);
        when(marcaRepository.save(any(Marca.class))).thenReturn(marcaEsperada);
        when(registrarMarcaMapper.toDto(marcaEsperada)).thenReturn(dto);

        // Ejecutar el método a probar
        RegistrarMarcaDto result = marcaService.registrarMarca(dto);

        // Verificar los resultados
        assertNotNull(result, "El resultado no debería ser nulo");
        assertEquals("MarcaValida", result.getNombre());

        // Verificar que se hayan llamado los métodos esperados
        verify(marcaRepository).existsByNombre("MarcaValida");
        verify(marcaRepository).save(marcaEsperada);
        verify(registrarMarcaMapper).toDto(marcaEsperada);
    }

    @Test
    public void testDescripcionNoSupera100Caracteres() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("NombreValido");

        // Descripción con más de 100 caracteres
        dto.setDescripcion("Esta es una descripción muy larga que tiene más de cien caracteres y debería causar una excepción en el servicio.");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        // Verificar que el mensaje de la excepción es el esperado
        assertEquals("La descripción no puede superar los 100 caracteres", exception.getMessage());
    }

    @Test
    public void testDescripcionNoContieneCaracteresNumericos() {
        // Arrange
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("NombreValido");

        // Descripción que contiene caracteres numéricos
        dto.setDescripcion("Esta descripción tiene números 123 y debería causar una excepción.");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.registrarMarca(dto);
        });

        // Verificar que el mensaje de la excepción es el esperado
        assertEquals("La descripción no puede contener caracteres numéricos", exception.getMessage());
    }
}
