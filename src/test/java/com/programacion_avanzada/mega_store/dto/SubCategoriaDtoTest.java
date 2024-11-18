package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SubCategoriaDtoTest {

    private SubCategoriaDTO subCategoriaDto;

    @BeforeEach
    public void setUp() {
        subCategoriaDto = new SubCategoriaDTO();
    }

    @Test
    public void testNombreConMenosDeDosCaracteres() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        subCategoriaDto.setDescripcion("Descripción válida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        Assertions.assertFalse(subCategoriaDto.esValido(), "El nombre no debe tener menos de 2 caracteres.");    }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        subCategoriaDto.setDescripcion("Descripción válida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        subCategoriaDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");//Nombre con mas de 64 caracteres
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        Assertions.assertFalse(subCategoriaDto.esValido(), "El nombre no debe tener más de 64 caracteres.");
    }

    @Test
    public void testNombreNoDebeContenerEspaciosEnBlanco() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        subCategoriaDto.setDescripcion("Descripción válida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        subCategoriaDto.setNombre("Nombre Con Espacios"); // Nombre con espacios
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        Assertions.assertFalse(subCategoriaDto.esValido(), "El nombre no debe contener espacios en blanco.");
    }

    @Test
    public void testNombreConNumeros() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        subCategoriaDto.setDescripcion("Descripción válida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        subCategoriaDto.setNombre("Nombre123"); // Nombre no válido por contener números
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        Assertions.assertFalse(subCategoriaDto.esValido(), "El nombre no debe contener números.");
    }

    @Test
    public void testNombreYDescripcionValidosConCaracteresAlfabeticos() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        subCategoriaDto.setDescripcion("Descripción válida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        //Datos validos
        subCategoriaDto.setNombre("NombreVálido");
        subCategoriaDto.setDescripcion("Descripcion válida");
        subCategoriaDto.setCategoriaDto(categoriaDto);


        assertTrue(subCategoriaDto.esValido(), "El nombre y la descripción deberían ser válidos.");
    }

    @Test
    public void testNombreYDescripcionValidos() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        subCategoriaDto.setDescripcion("Descripción válida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        //Datos validos
        subCategoriaDto.setNombre("NombreValido");
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        assertTrue(subCategoriaDto.esValido(), "El nombre y la descripción deberían ser válidos.");
    }

    @Test
    public void testDescripcionConMenosDeDosCaracteres() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        subCategoriaDto.setDescripcion("Descripción válida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        subCategoriaDto.setNombre("NombreValido");
        subCategoriaDto.setDescripcion("D"); // Descripcion de solo 1 carácter, debería ser inválido
        subCategoriaDto.setCategoriaDto(categoriaDto);

        Assertions.assertFalse(subCategoriaDto.esValido(), "La descripcion no debe tener menos de 2 caracteres.");
    }

    @Test
    public void testDescripcionConMasDeCienCaracteres() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        subCategoriaDto.setDescripcion("Descripción válida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        subCategoriaDto.setNombre("NombreValido");
        subCategoriaDto.setDescripcion("EstaDescripcionEsDemasiadoLargaComoParaSerValidaYAExcedeLosCienCaracteresPorqueEstaCadenaEsExcesivaYNoDeberiaSerAceptada");
        //Descripcion con mas de 100 caracteres
        subCategoriaDto.setCategoriaDto(categoriaDto);
        Assertions.assertFalse(subCategoriaDto.esValido(), "La descripción no debe tener más de 100 caracteres.");
    }

    @Test
    public void testDescripcionConNumeros() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        subCategoriaDto.setDescripcion("Descripción válida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        subCategoriaDto.setNombre("NombreVálido");
        subCategoriaDto.setDescripcion("Descripción con número 123"); // Descripción con números
        subCategoriaDto.setCategoriaDto(categoriaDto);

        Assertions.assertFalse(subCategoriaDto.esValido(), "La descripción no debe ser válida si contiene números.");
    }

    @Test
    public void testDescripcionConEspacios() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        subCategoriaDto.setDescripcion("Descripción válida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        subCategoriaDto.setNombre("NombreVálido");
        subCategoriaDto.setDescripcion("Descripcion con espacios"); //Descripcion con espacios en blanco
        subCategoriaDto.setCategoriaDto(categoriaDto);


        assertTrue(subCategoriaDto.esValido(), "La descripción debería ser válida.");
    }
}
