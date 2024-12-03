package com.programacion_avanzada.mega_store.service;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;
import com.programacion_avanzada.mega_store.Mapper.RegistrarSubCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;
import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;
import com.programacion_avanzada.mega_store.Service.SubCategoriaService;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SubCategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private SubCategoriaRepository subCategoriaRepository;

    @Mock
    private RegistrarSubCategoriaMapper registrarSubCategoriaMapper;

    @InjectMocks
    private SubCategoriaService subCategoriaService;

    private SubCategoriaDTO subCategoriaDto;

    @BeforeEach
    public void setUp() {
        subCategoriaDto = new SubCategoriaDTO();
        // No es necesario crear manualmente la instancia de categoriaService,
        // ya que Mockito se encarga de ello con @InjectMocks
    }

        @Test
        void testRegistrarSubCategoria() {
            // Crear un objeto DTO de prueba
            RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
            dto.setNombre("SubCategoriaValida");
            dto.setDescripcion("Descripción válida");
            dto.setCategoriaId(1L);

            // Crear un objeto Categoria para asociarlo a SubCategoria
            Categoria categoria = new Categoria();
            categoria.setId(1L);
            categoria.setNombre("CategoriaPrincipal");

            // Configurar el comportamiento del mock para la categoría
            when(categoriaRepository.existsById(1L)).thenReturn(true);
            when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

            // Configurar el comportamiento del mock para que devuelva una nueva SubCategoria
            SubCategoria subCategoriaEsperada = new SubCategoria();
            subCategoriaEsperada.setNombre("SubCategoriaValida");
            subCategoriaEsperada.setDescripcion("Descripción válida");
            subCategoriaEsperada.setCategoria(categoria);
            when(registrarSubCategoriaMapper.toEntity(dto)).thenReturn(subCategoriaEsperada);

            // Ahora llama al método después de haber configurado el mock
            SubCategoria subCategoria = registrarSubCategoriaMapper.toEntity(dto);

            // Simular el comportamiento del repositorio
            when(subCategoriaRepository.existsByNombre(anyString())).thenReturn(false);
            when(subCategoriaRepository.save(any(SubCategoria.class))).thenReturn(subCategoria);
            when(registrarSubCategoriaMapper.toDto(subCategoria)).thenReturn(dto);

            // Ejecutar el metodo a probar
            RegistrarSubCategoriaDto result = subCategoriaService.registrarSubCategoria(dto);

            // Verificar los resultados
            assertNotNull(result, "El resultado no debería ser nulo");
            assertEquals("SubCategoriaValida", result.getNombre());
            assertEquals("Descripción válida", result.getDescripcion());

            // Verificar que se hayan llamado los métodos esperados
            verify(subCategoriaRepository).existsByNombre("SubCategoriaValida");
            verify(subCategoriaRepository).save(subCategoriaEsperada);
            verify(categoriaRepository).existsById(1L);
        }

    @Test
    void testRegistrarSubCategoriaYaExistente() {
        // Crear un objeto DTO de prueba
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("SubCategoriaExistente");
        dto.setDescripcion("Descripción existente");
        dto.setCategoriaId(1L); // Asume que esta categoría existe

        // Simular que la categoría existe
        when(categoriaRepository.existsById(1L)).thenReturn(true);
        // Simular que la subcategoría ya existe en el repositorio
        when(subCategoriaRepository.existsByNombre("SubCategoriaExistente")).thenReturn(true);

        // Ejecutar el metodo a probar y verificar que se lanza la excepción
        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
            subCategoriaService.registrarSubCategoria(dto);
        });

        assertEquals("La subcategoria ya existe", exception.getMessage());
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


