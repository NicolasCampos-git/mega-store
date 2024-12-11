package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.RegistrarMarcaDto;
import com.programacion_avanzada.mega_store.Service.MarcaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RegistrarMarcaDtoTest {

    private RegistrarMarcaDto marcaDto;
    private MarcaService marcaService;

    @BeforeEach
    public void setUp() {
        marcaDto = new RegistrarMarcaDto();
        marcaService = new MarcaService();
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
