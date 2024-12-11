package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.RegistrarCategoriaDto;
import com.programacion_avanzada.mega_store.Service.CategoriaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegistrarCategoriaDtoTest {

    private RegistrarCategoriaDto categoriaDto;
    private CategoriaService categoriaService;

    @BeforeEach
    public void setUp() {
        categoriaDto = new RegistrarCategoriaDto();
        categoriaService = new CategoriaService();
    }

    @Test
    public void testNombreConMenosDeDosCaracteres() {
        categoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        categoriaDto.setDescripcion("Descripción válida");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.validarNombre(categoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre de la categoría debe tener entre 2 y 64 caracteres.", exception.getMessage());   }

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
