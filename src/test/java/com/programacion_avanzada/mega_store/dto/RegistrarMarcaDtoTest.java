package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.RegistrarMarcaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RegistrarMarcaDtoTest {

    private RegistrarMarcaDto marcaDto;

    @BeforeEach
    public void setUp() {
        marcaDto = new RegistrarMarcaDto();
    }

    @Test
    public void testNombreConMenosDeDosCaracteres() {
        marcaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        marcaDto.setDescripcion("Descripcion valida");

        assertFalse(marcaDto.esValido(), "El nombre no debe tener menos de 2 caracteres.");
    }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        marcaDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");// Nombre de mas de 64 carácteres, debería ser inválido
        marcaDto.setDescripcion("Descripcion valida");

        assertFalse(marcaDto.esValido(), "El nombre no debe tener más de 64 caracteres.");
    }

    @Test
    public void testNombreConNumeros() {
        marcaDto.setNombre("Nombre123"); // Nombre no válido por contener números
        marcaDto.setDescripcion("Descripcion valida");

        assertFalse(marcaDto.esValido(), "El nombre no debe contener números.");
    }

    @Test
    public void testDescripcionConMasDeCienCaracteres() {
        marcaDto.setNombre("NombreValido");
        marcaDto.setDescripcion("EstaDescripcionEsDemasiadoLargaComoParaSerValidaYAExcedeLosCienCaracteresPorqueEstaCadenaEsExcesivaYNoDeberiaSerAceptada");// Descripcion de mas de 100 carácteres, debería ser inválido

        assertFalse(marcaDto.esValido(), "La descripción no debe tener más de 100 caracteres.");
    }

    @Test
    public void testNombreNoDebeContenerEspaciosEnBlanco() {
        marcaDto.setNombre("Nombre Con Espacios"); // Nombre con espacios
        marcaDto.setDescripcion("Descripcion valida");

        assertFalse(marcaDto.esValido(), "El nombre no debe contener espacios en blanco.");
    }

    @Test
    public void testNombreYDescripcionValidos() {
        marcaDto.setNombre("NombreValido");// Nombre valido
        marcaDto.setDescripcion("Descripcion valida"); // Descripcion valida

        assertTrue(marcaDto.esValido(), "El nombre y la descripción deberían ser válidos.");
    }

    @Test
    public void testCrearMarcaConDescripcionNula() {
        marcaDto.setNombre("NombreVálido");
        marcaDto.setDescripcion(null); // Descripción nula

        assertTrue(marcaDto.esValido(), "El DTO debe ser válido con descripción nula.");
    }

    @Test
    public void testDescripcionConNumeros() {
        marcaDto.setNombre("NombreVálido");
        marcaDto.setDescripcion("Descripción con número 123"); // Descripción con números

        assertFalse(marcaDto.esValido(), "La descripción no debe ser válida si contiene números.");
    }
}
