package com.programacion_avanzada.mega_store.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
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
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MarcaServiceTest {

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private RegistrarMarcaMapper registrarMarcaMapper;

    @InjectMocks
    private MarcaService marcaService;

    private MarcaDto marcaDto;

    @BeforeEach
    public void setUp() {
        marcaDto = new MarcaDto();
        // No es necesario crear manualmente la instancia de categoriaService,
        // ya que Mockito se encarga de ello con @InjectMocks
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

        // Ahora llama al metodo después de haber configurado el mock
        Marca marca = registrarMarcaMapper.toEntity(dto);

        // Simular el comportamiento del repositorio
        when(marcaRepository.existsByNombre(anyString())).thenReturn(false);
        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);
        when(registrarMarcaMapper.toDto(marca)).thenReturn(dto); // Supongamos que también quieres devolver el DTO.

        // Ejecutar el metodo a probar
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
    public void testNombreConMenosDeDosCaracteres() {
        marcaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        marcaDto.setDescripcion("Descripcion valida");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.validarNombre(marcaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre de la marca debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        marcaDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");// Nombre de mas de 64 carácteres, debería ser inválido
        marcaDto.setDescripcion("Descripcion valida");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.validarNombre(marcaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre de la marca debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testNombreConNumeros() {
        marcaDto.setNombre("Nombre123"); // Nombre no válido por contener números
        marcaDto.setDescripcion("Descripcion valida");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.validarNombre(marcaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre no debe contener números.", exception.getMessage());
    }

    @Test
    public void testDescripcionConMasDeCienCaracteres() {
        marcaDto.setNombre("NombreValido");
        marcaDto.setDescripcion("EstaDescripcionEsDemasiadoLargaComoParaSerValidaYAExcedeLosCienCaracteresPorqueEstaCadenaEsExcesivaYNoDeberiaSerAceptada");// Descripcion de mas de 100 carácteres, debería ser inválido

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.validarDescripcion(marcaDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion de la marca debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testNombreNoDebeContenerEspaciosEnBlanco() {
        marcaDto.setNombre("Nombre Con Espacios"); // Nombre con espacios
        marcaDto.setDescripcion("Descripcion valida");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.validarNombre(marcaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre de la marca no debe contener espacios.", exception.getMessage());
    }

    @Test
    public void testNombreYDescripcionValidos() {
        marcaDto.setNombre("NombreValido");// Nombre valido
        marcaDto.setDescripcion("Descripcion valida"); // Descripcion valida

        marcaService.validarNombre(marcaDto.getNombre());
        marcaService.validarDescripcion(marcaDto.getDescripcion());
    }

    @Test
    public void testCrearMarcaConDescripcionNula() {
        marcaDto.setNombre("NombreVálido");
        marcaDto.setDescripcion(null); // Descripción nula

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.validarDescripcion(marcaDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion de la marca no puede estar vacía.", exception.getMessage());
    }

    @Test
    public void testDescripcionConNumeros() {
        marcaDto.setNombre("NombreVálido");
        marcaDto.setDescripcion("Descripción con número 123"); // Descripción con números

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.validarDescripcion(marcaDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion no debe contener números.", exception.getMessage());
    }
}
