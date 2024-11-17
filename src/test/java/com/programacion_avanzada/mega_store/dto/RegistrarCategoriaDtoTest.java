package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.RegistrarCategoriaDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RegistrarCategoriaDtoTest {

    private RegistrarCategoriaDto categoriaDto;

    @BeforeEach
    public void setUp() {
        categoriaDto = new RegistrarCategoriaDto();
    }

    @Test
    public void testNombreConMenosDeDosCaracteres() {
        categoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        categoriaDto.setDescripcion("Descripción válida");

        Assertions.assertFalse(categoriaDto.esValido(), "El nombre no debe tener menos de 2 caracteres.");    }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        categoriaDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");// Nombre de mas de 64 carácteres, debería ser inválido
        categoriaDto.setDescripcion("Descripcion valida");

        Assertions.assertFalse(categoriaDto.esValido(), "El nombre no debe tener más de 64 caracteres.");
    }

    @Test
    public void testNombreNoDebeContenerEspaciosEnBlanco() {
        categoriaDto.setNombre("Nombre Con Espacios"); // Nombre con espacios
        categoriaDto.setDescripcion("Descripcion valida");

        Assertions.assertFalse(categoriaDto.esValido(), "El nombre no debe contener espacios en blanco.");
    }

    @Test
    public void testNombreConNumeros() {
        categoriaDto.setNombre("Nombre123"); // Nombre no válido por contener números
        categoriaDto.setDescripcion("Descripcion valida");

        Assertions.assertFalse(categoriaDto.esValido(), "El nombre no debe contener números.");
    }

    @Test
    public void testDescripcionConMenosDeDosCaracteres() {
        categoriaDto.setNombre("NombreValido");
        categoriaDto.setDescripcion("D"); // Descripcion de solo 1 carácter, debería ser inválido

        Assertions.assertFalse(categoriaDto.esValido(), "La categoria no debe tener menos de 2 caracteres.");
    }

    @Test
    public void testDescripcionConMasDeCienCaracteres() {
        categoriaDto.setNombre("NombreValido");
        categoriaDto.setDescripcion("EstaDescripcionEsDemasiadoLargaComoParaSerValidaYAExcedeLosCienCaracteresPorqueEstaCadenaEsExcesivaYNoDeberiaSerAceptada");// Descripcion de mas de 100 carácteres, debería ser inválido

        Assertions.assertFalse(categoriaDto.esValido(), "La descripción no debe tener más de 100 caracteres.");
    }

    @Test
    public void testDescripcionConNumeros() {
        categoriaDto.setNombre("NombreVálido");
        categoriaDto.setDescripcion("Descripción con número 123"); // Descripción con números

        Assertions.assertFalse(categoriaDto.esValido(), "La descripción no debe ser válida si contiene números.");
    }

    @Test
    public void testNombreYDescripcionValidos() {
        categoriaDto.setNombre("NombreValido");// Nombre valido
        categoriaDto.setDescripcion("Descripcion valida");// Descripcion valida

        assertTrue(categoriaDto.esValido(), "El nombre y la descripción deberían ser válidos.");
    }

    @Test
    public void testNombreYDescripcionValidosConCaracteresAlfabeticos() {
        categoriaDto.setNombre("NombreVálido");// Nombre valido
        categoriaDto.setDescripcion("Descripcion válida");// Descripcion valida

        assertTrue(categoriaDto.esValido(), "El nombre y la descripción deberían ser válidos.");
    }
}
