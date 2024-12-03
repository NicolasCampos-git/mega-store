package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.Service.SubCategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegistrarSubCategoriaDtoTest {

    private RegistrarSubCategoriaDto subCategoriaDto;
    private SubCategoriaService subCategoriaService;

    @BeforeEach
    public void setUp() {
        subCategoriaDto = new RegistrarSubCategoriaDto();
        subCategoriaService = new SubCategoriaService();
    }

    @Test
    public void testNombreConMenosDeDosCaracteres() {
        subCategoriaDto.setNombre("A"); // Nombre de solo 1 carácter, debería ser inválido
        subCategoriaDto.setDescripcion("Descripción válida");
        subCategoriaDto.setCategoriaId(1L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarNombre(subCategoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre debe tener entre 2 y 64 caracteres.", exception.getMessage()); }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        subCategoriaDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");//Nombre con mas de 64 caracteres
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaId(1L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarNombre(subCategoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testNombreNoDebeContenerEspaciosEnBlanco() {
        subCategoriaDto.setNombre("Nombre Con Espacios"); // Nombre con espacios
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaId(1L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarNombre(subCategoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre no debe contener espacios.", exception.getMessage());
    }

    @Test
    public void testNombreConNumeros() {
        subCategoriaDto.setNombre("Nombre123"); // Nombre no válido por contener números
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaId(1L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarNombre(subCategoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre no debe contener números.", exception.getMessage());
    }

    @Test
    public void testNombreYDescripcionValidosConCaracteresAlfabeticos() {
        //Datos validos
        subCategoriaDto.setNombre("NombreVálido");
        subCategoriaDto.setDescripcion("Descripcion válida");
        subCategoriaDto.setCategoriaId(1L);


        subCategoriaService.valirdarNombre(subCategoriaDto.getNombre());
        subCategoriaService.valirdarDescripcion(subCategoriaDto.getDescripcion());
    }

    @Test
    public void testNombreYDescripcionValidos() {
        //Datos validos
        subCategoriaDto.setNombre("NombreValido");
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaId(1L);

        subCategoriaService.valirdarNombre(subCategoriaDto.getNombre());
        subCategoriaService.valirdarDescripcion(subCategoriaDto.getDescripcion());
    }

    @Test
    public void testDescripcionConMenosDeDosCaracteres() {
        subCategoriaDto.setNombre("NombreValido");
        subCategoriaDto.setDescripcion("D"); // Descripcion de solo 1 carácter, debería ser inválido
        subCategoriaDto.setCategoriaId(1L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarDescripcion(subCategoriaDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testDescripcionConMasDeCienCaracteres() {
        subCategoriaDto.setNombre("NombreValido");
        subCategoriaDto.setDescripcion("EstaDescripcionEsDemasiadoLargaComoParaSerValidaYAExcedeLosCienCaracteresPorqueEstaCadenaEsExcesivaYNoDeberiaSerAceptada");
        //Descripcion con mas de 100 caracteres
        subCategoriaDto.setCategoriaId(1L);
        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarDescripcion(subCategoriaDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testDescripcionConNumeros() {
        subCategoriaDto.setNombre("NombreVálido");
        subCategoriaDto.setDescripcion("Descripción con número 123"); // Descripción con números
        subCategoriaDto.setCategoriaId(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarDescripcion(subCategoriaDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion no debe contener números.", exception.getMessage());
    }

    @Test
    public void testDescripcionConEspacios() {
        subCategoriaDto.setNombre("NombreVálido");
        subCategoriaDto.setDescripcion("Descripcion con espacios"); //Descripcion con espacios en blanco
        subCategoriaDto.setCategoriaId(1L);


        subCategoriaService.valirdarNombre(subCategoriaDto.getNombre());
        subCategoriaService.valirdarDescripcion(subCategoriaDto.getDescripcion());
    }
}