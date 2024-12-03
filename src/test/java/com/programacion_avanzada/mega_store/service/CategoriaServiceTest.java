package com.programacion_avanzada.mega_store.service;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.RegistrarCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;
import com.programacion_avanzada.mega_store.Service.CategoriaService;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private RegistrarCategoriaMapper registrarCategoriaMapper;

    @InjectMocks
    private CategoriaService categoriaService;

    private CategoriaDto categoriaDto;

    @BeforeEach
    public void setUp() {
        categoriaDto = new CategoriaDto();
        // No es necesario crear manualmente la instancia de categoriaService,
        // ya que Mockito se encarga de ello con @InjectMocks
    }

    @Test
    void testRegistrarCategoria() {
        // Crear un objeto DTO de prueba
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("CategoriaValida");
        dto.setDescripcion("Descripción válida");

        // Crear la categoría esperada
        Categoria categoriaEsperada = new Categoria();
        categoriaEsperada.setNombre("CategoriaValida");
        categoriaEsperada.setDescripcion("Descripción válida");

        // Configurar el comportamiento del mock para que devuelva la categoría esperada
        when(registrarCategoriaMapper.toEntity(dto)).thenReturn(categoriaEsperada);

        // Simular el comportamiento del repositorio
        when(categoriaRepository.existsByNombre(anyString())).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaEsperada);
        when(registrarCategoriaMapper.toDto(categoriaEsperada)).thenReturn(dto);

        // Ejecutar el método de registrar categoría
        RegistrarCategoriaDto result = categoriaService.registrarCategoria(dto);

        // Verificar que el resultado no sea nulo
        assertNotNull(result, "El resultado no debería ser nulo");
        assertEquals("CategoriaValida", result.getNombre());
        assertEquals("Descripción válida", result.getDescripcion());

        // Verificar que se hayan llamado los métodos esperados
        verify(categoriaRepository).existsByNombre("CategoriaValida");
        verify(categoriaRepository).save(categoriaEsperada);
        verify(registrarCategoriaMapper).toDto(categoriaEsperada);
    }

    @Test
    void testRegistrarCategoriaYaExistente() {
        // Crear un objeto DTO de prueba
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("CategoriaExistente");
        dto.setDescripcion("Descripción existente");

        // Configurar el comportamiento del mock para que devuelva una nueva Categoria
        Categoria categoriaEsperada = new Categoria();
        categoriaEsperada.setNombre("CategoriaValida");
        categoriaEsperada.setDescripcion("Descripción válida");
        when(registrarCategoriaMapper.toEntity(dto)).thenReturn(categoriaEsperada);

        // Simular que la categoria ya existe en el repositorio
        when(categoriaRepository.existsByNombre(anyString())).thenReturn(true);

        // Ejecutar el método a probar y verificar que se lanza la excepción
        Exception exception = assertThrows(EntityExistsException.class, () -> {
            categoriaService.registrarCategoria(dto);
        });

        assertEquals("La categoria ya existe", exception.getMessage());
    }

    @Test
    public void testNombreConMenosDeDosCaracteres() {
        categoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        categoriaDto.setDescripcion("Descripción válida");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.validarNombre(categoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre de la categoría debe tener entre 2 y 64 caracteres.", exception.getMessage());    }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        categoriaDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");// Nombre de mas de 64 carácteres, debería ser inválido
        categoriaDto.setDescripcion("Descripcion valida");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.validarNombre(categoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre de la categoría debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testNombreNoDebeContenerEspaciosEnBlanco() {
        categoriaDto.setNombre("Nombre Con Espacios"); // Nombre con espacios
        categoriaDto.setDescripcion("Descripcion valida");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.validarNombre(categoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre de la categoría no debe contener espacios.", exception.getMessage());
    }

    @Test
    public void testNombreConNumeros() {
        categoriaDto.setNombre("Nombre123"); // Nombre no válido por contener números
        categoriaDto.setDescripcion("Descripcion valida");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.validarNombre(categoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre no debe contener números.", exception.getMessage());
    }

    @Test
    public void testDescripcionConMenosDeDosCaracteres() {
        categoriaDto.setNombre("NombreValido");
        categoriaDto.setDescripcion("D"); // Descripcion de solo 1 carácter, debería ser inválido

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.validarDescripcion(categoriaDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripción de la categoría debe tener entre 2 y 100 caracteres.", exception.getMessage());
    }

    @Test
    public void testDescripcionConMasDeCienCaracteres() {
        categoriaDto.setNombre("NombreValido");
        categoriaDto.setDescripcion("EstaDescripcionEsDemasiadoLargaComoParaSerValidaYAExcedeLosCienCaracteresPorqueEstaCadenaEsExcesivaYNoDeberiaSerAceptada");// Descripcion de mas de 100 carácteres, debería ser inválido

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.validarDescripcion(categoriaDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripción de la categoría debe tener entre 2 y 100 caracteres.", exception.getMessage());
    }

    @Test
    public void testDescripcionConNumeros() {
        categoriaDto.setNombre("NombreVálido");
        categoriaDto.setDescripcion("Descripción con número 123"); // Descripción con números

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.validarDescripcion(categoriaDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripción no puede contener números.", exception.getMessage());
    }

    @Test
    public void testNombreYDescripcionValidos() {
        categoriaDto.setNombre("NombreValido");// Nombre valido
        categoriaDto.setDescripcion("Descripcion valida");// Descripcion valida

        categoriaService.validarNombre(categoriaDto.getNombre());
        categoriaService.validarDescripcion(categoriaDto.getDescripcion());
    }

    @Test
    public void testNombreYDescripcionValidosConCaracteresAlfabeticos() {
        categoriaDto.setNombre("NombreVálido");// Nombre valido
        categoriaDto.setDescripcion("Descripcion válida");// Descripcion valida

        categoriaService.validarNombre(categoriaDto.getNombre());
        categoriaService.validarDescripcion(categoriaDto.getDescripcion());
    }
}
