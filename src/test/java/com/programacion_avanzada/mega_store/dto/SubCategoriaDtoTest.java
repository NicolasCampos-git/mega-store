package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDto;
import com.programacion_avanzada.mega_store.Service.SubCategoriaService;
import com.programacion_avanzada.mega_store.Service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubCategoriaDtoTest {

    private SubCategoriaDTO subCategoriaDto;
    private SubCategoriaService subCategoriaService;

    @BeforeEach
    public void setUp() {
        // Instanciamos UsuarioService
        subCategoriaService = new SubCategoriaService();
        // Instanciamos UsuarioDto
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

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarNombre(subCategoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre debe tener entre 2 y 64 caracteres.", exception.getMessage());    }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");//Nombre con mas de 64 caracteres
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarNombre(subCategoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testNombreNoDebeContenerEspaciosEnBlanco() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("Nombre Con Espacios"); // Nombre con espacios
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarNombre(subCategoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre no debe contener espacios.", exception.getMessage());
    }

    @Test
    public void testNombreConNumeros() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("Nombre123"); // Nombre no válido por contener números
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarNombre(subCategoriaDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre no debe contener números.", exception.getMessage());
    }

    @Test
    public void testNombreYDescripcionValidosConCaracteresAlfabeticos() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        //Datos validos
        subCategoriaDto.setNombre("NombreVálido");
        subCategoriaDto.setDescripcion("Descripcion válida");
        subCategoriaDto.setCategoriaDto(categoriaDto);


        subCategoriaService.valirdarNombre(subCategoriaDto.getNombre());
        subCategoriaService.valirdarDescripcion(subCategoriaDto.getDescripcion());
    }

    @Test
    public void testNombreYDescripcionValidos() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        //Datos validos
        subCategoriaDto.setNombre("NombreValido");
        subCategoriaDto.setDescripcion("Descripcion valida");
        subCategoriaDto.setCategoriaDto(categoriaDto);

        subCategoriaService.valirdarNombre(subCategoriaDto.getNombre());
        subCategoriaService.valirdarDescripcion(subCategoriaDto.getDescripcion());
    }

    @Test
    public void testDescripcionConMenosDeDosCaracteres() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("NombreValido");
        subCategoriaDto.setDescripcion("D"); // Descripcion de solo 1 carácter, debería ser inválido
        subCategoriaDto.setCategoriaDto(categoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarDescripcion(subCategoriaDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testDescripcionConMasDeCienCaracteres() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("NombreValido");
        subCategoriaDto.setDescripcion("EstaDescripcionEsDemasiadoLargaComoParaSerValidaYAExcedeLosCienCaracteresPorqueEstaCadenaEsExcesivaYNoDeberiaSerAceptada");
        //Descripcion con mas de 100 caracteres
        subCategoriaDto.setCategoriaDto(categoriaDto);
        // Simulamos la validación

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarDescripcion(subCategoriaDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testDescripcionConNumeros() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("NombreVálido");
        subCategoriaDto.setDescripcion("Descripción con número 123"); // Descripción con números
        subCategoriaDto.setCategoriaDto(categoriaDto);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.valirdarDescripcion(subCategoriaDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion no debe contener números.", exception.getMessage());
    }

    @Test
    public void testDescripcionConEspacios() {
        // Crear objeto Categoria para asociarlo a la SubCategoria
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("MarcaValida");

        subCategoriaDto.setNombre("NombreVálido");
        subCategoriaDto.setDescripcion("Descripcion con espacios"); //Descripcion con espacios en blanco
        subCategoriaDto.setCategoriaDto(categoriaDto);

        subCategoriaService.valirdarNombre(subCategoriaDto.getNombre());
        subCategoriaService.valirdarDescripcion(subCategoriaDto.getDescripcion());

    }
}
