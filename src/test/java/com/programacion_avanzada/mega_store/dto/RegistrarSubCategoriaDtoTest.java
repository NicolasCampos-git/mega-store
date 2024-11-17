package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.RegistrarSubCategoriaDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RegistrarSubCategoriaDtoTest {

    private RegistrarSubCategoriaDto subCategoriaDto;

    @BeforeEach
    public void setUp() {
        subCategoriaDto = new RegistrarSubCategoriaDto();
    }

    @Test
    public void testNombreConMenosDeDosCaracteres() {
        subCategoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        subCategoriaDto.setDescripcion("Descripción válida");
        subCategoriaDto.setCategoriaId(1L);

        Assertions.assertFalse(subCategoriaDto.esValido(), "El nombre no debe tener menos de 2 caracteres.");    }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        subCategoriaDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");//Nombre con mas de 64 caracteres
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaId(1L);

        Assertions.assertFalse(subCategoriaDto.esValido(), "El nombre no debe tener más de 64 caracteres.");
    }

    @Test
    public void testNombreNoDebeContenerEspaciosEnBlanco() {
        subCategoriaDto.setNombre("Nombre Con Espacios"); // Nombre con espacios
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaId(1L);

        Assertions.assertFalse(subCategoriaDto.esValido(), "El nombre no debe contener espacios en blanco.");
    }

    @Test
    public void testNombreConNumeros() {
        subCategoriaDto.setNombre("Nombre123"); // Nombre no válido por contener números
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaId(1L);

        Assertions.assertFalse(subCategoriaDto.esValido(), "El nombre no debe contener números.");
    }

    @Test
    public void testNombreYDescripcionValidosConCaracteresAlfabeticos() {
        //Datos validos
        subCategoriaDto.setNombre("NombreVálido");
        subCategoriaDto.setDescripcion("Descripcion válida");
        subCategoriaDto.setCategoriaId(1L);


        assertTrue(subCategoriaDto.esValido(), "El nombre y la descripción deberían ser válidos.");
    }

    @Test
    public void testNombreYDescripcionValidos() {
        //Datos validos
        subCategoriaDto.setNombre("NombreValido");
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaId(1L);

        assertTrue(subCategoriaDto.esValido(), "El nombre y la descripción deberían ser válidos.");
    }

    @Test
    public void testDescripcionConMenosDeDosCaracteres() {
        subCategoriaDto.setNombre("NombreValido");
        subCategoriaDto.setDescripcion("D"); // Descripcion de solo 1 carácter, debería ser inválido
        subCategoriaDto.setCategoriaId(1L);

        Assertions.assertFalse(subCategoriaDto.esValido(), "La descripcion no debe tener menos de 2 caracteres.");
    }

    @Test
    public void testDescripcionConMasDeCienCaracteres() {
        subCategoriaDto.setNombre("NombreValido");
        subCategoriaDto.setDescripcion("EstaDescripcionEsDemasiadoLargaComoParaSerValidaYAExcedeLosCienCaracteresPorqueEstaCadenaEsExcesivaYNoDeberiaSerAceptada");
        //Descripcion con mas de 100 caracteres
        subCategoriaDto.setCategoriaId(1L);
        Assertions.assertFalse(subCategoriaDto.esValido(), "La descripción no debe tener más de 100 caracteres.");
    }

    @Test
    public void testDescripcionConNumeros() {
        subCategoriaDto.setNombre("NombreVálido");
        subCategoriaDto.setDescripcion("Descripción con número 123"); // Descripción con números
        subCategoriaDto.setCategoriaId(1L);

        Assertions.assertFalse(subCategoriaDto.esValido(), "La descripción no debe ser válida si contiene números.");
    }

    @Test
    public void testDescripcionConEspacios() {
        subCategoriaDto.setNombre("NombreVálido");
        subCategoriaDto.setDescripcion("Descripcion con espacios"); //Descripcion con espacios en blanco
        subCategoriaDto.setCategoriaId(1L);


        assertTrue(subCategoriaDto.esValido(), "La descripción debería ser válida.");
    }
}
