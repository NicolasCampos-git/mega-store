package com.programacion_avanzada.TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDtos.RegistrarCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.CategoriaMappers.CategoriaMapper;
import com.programacion_avanzada.mega_store.Mapper.CategoriaMappers.RegistrarCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;
import com.programacion_avanzada.mega_store.Service.CategoriaService;

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private RegistrarCategoriaMapper registrarCategoriaMapper;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarCategoria_ConNombreMinimo_DebeRegistrarExitosamente() {
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("aa"); // mínimo 2 caracteres
        dto.setDescripcion("Categoría ejemplo");

        Categoria categoria = new Categoria();
        categoria.setNombre("aa");
        categoria.setDescripcion("Categoría ejemplo");

        when(registrarCategoriaMapper.toEntity(any(RegistrarCategoriaDto.class))).thenReturn(categoria);
        when(categoriaRepository.existsByNombre(any())).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        when(registrarCategoriaMapper.toDto(any(Categoria.class))).thenReturn(dto);

        RegistrarCategoriaDto resultado = categoriaService.registrarCategoria(dto);

        assertNotNull(resultado);
        assertEquals("aa", resultado.getNombre());
        assertEquals("Categoría ejemplo", resultado.getDescripcion());
    }


    @Test
    void registrarCategoria_ConNombreVacio_DebeLanzarExcepcion() {
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("");
        dto.setDescripcion("Categoría ejemplo");

        Categoria categoria = new Categoria();
        categoria.setNombre("");
        categoria.setDescripcion("Categoría ejemplo");

        when(registrarCategoriaMapper.toEntity(any(RegistrarCategoriaDto.class))).thenReturn(categoria);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.registrarCategoria(dto);
        });

        assertEquals("El nombre de la categoría no puede estar vacío.", exception.getMessage());
    }

    @Test
    void registrarCategoria_ConNombreDeLongitudMenor_DebeLanzarExcepcion() {
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("A");
        dto.setDescripcion("Categoría ejemplo");

        Categoria categoria = new Categoria();
        categoria.setNombre("A");
        categoria.setDescripcion("Categoría ejemplo");

        when(registrarCategoriaMapper.toEntity(any(RegistrarCategoriaDto.class))).thenReturn(categoria);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.registrarCategoria(dto);
        });

        assertEquals("El nombre de la categoría debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    void registrarCategoria_ConNombreDeLongitudMayor_DebeLanzarExcepcion() {
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("EsteEsUnNombreDeEjemploExtremadamenteLargoQueSuperaLos64CaracteresFáciles");
        dto.setDescripcion("Categoría ejemplo");

        Categoria categoria = new Categoria();
        categoria.setNombre("EsteEsUnNombreDeEjemploExtremadamenteLargoQueSuperaLos64CaracteresFáciles");
        categoria.setDescripcion("Categoría ejemplo");

        when(registrarCategoriaMapper.toEntity(any(RegistrarCategoriaDto.class))).thenReturn(categoria);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.registrarCategoria(dto);
        });

        assertEquals("El nombre de la categoría debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    void registrarCategoria_ConNombreConEspacios_DebeLanzarExcepcion() {
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("A a");
        dto.setDescripcion("Categoría ejemplo");

        Categoria categoria = new Categoria();
        categoria.setNombre("A a");
        categoria.setDescripcion("Categoría ejemplo");

        when(registrarCategoriaMapper.toEntity(any(RegistrarCategoriaDto.class))).thenReturn(categoria);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.registrarCategoria(dto);
        });

        assertEquals("El nombre de la categoría no debe contener espacios.", exception.getMessage());
    }

    @Test
    void registrarCategoria_ConNombreConNumeros_DebeLanzarExcepcion() {
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("123");
        dto.setDescripcion("Categoría ejemplo");

        Categoria categoria = new Categoria();
        categoria.setNombre("123");
        categoria.setDescripcion("Categoría ejemplo");

        when(registrarCategoriaMapper.toEntity(any(RegistrarCategoriaDto.class))).thenReturn(categoria);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.registrarCategoria(dto);
        });

        assertEquals("El nombre no debe contener números.", exception.getMessage());
    }


    @Test
    void registrarCategoria_ConDescripcionVacia_DebeLanzarExcepcion() {
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("electronica");
        dto.setDescripcion("");

        Categoria categoria = new Categoria();
        categoria.setNombre("electronica");
        categoria.setDescripcion("");

        when(registrarCategoriaMapper.toEntity(any(RegistrarCategoriaDto.class))).thenReturn(categoria);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.registrarCategoria(dto);
        });

        assertEquals("La descripción de la categoría no puede estar vacía.", exception.getMessage());
    }

    @Test
    void registrarCategoria_ConDescripcionLongitudMenor_DebeLanzarExcepcion() {
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("electronica");
        dto.setDescripcion("a");

        Categoria categoria = new Categoria();
        categoria.setNombre("electronica");
        categoria.setDescripcion("a");

        when(registrarCategoriaMapper.toEntity(any(RegistrarCategoriaDto.class))).thenReturn(categoria);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.registrarCategoria(dto);
        });

        assertEquals("La descripción de la categoría debe tener entre 2 y 100 caracteres.", exception.getMessage());
    }

    @Test
    void registrarCategoria_ConDescripcionLongitudMayor_DebeLanzarExcepcion() {
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("electronica");
        dto.setDescripcion("Esta es una descripción de ejemplo pensada para superar los cien caracteres y así poder probar límites de validación o almacenamiento en bases de datos, archivos u otros sistemas.");

        Categoria categoria = new Categoria();
        categoria.setNombre("electronica");
        categoria.setDescripcion("Esta es una descripción de ejemplo pensada para superar los cien caracteres y así poder probar límites de validación o almacenamiento en bases de datos, archivos u otros sistemas.");

        when(registrarCategoriaMapper.toEntity(any(RegistrarCategoriaDto.class))).thenReturn(categoria);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.registrarCategoria(dto);
        });

        assertEquals("La descripción de la categoría debe tener entre 2 y 100 caracteres.", exception.getMessage());
    }

    @Test
    void registrarCategoria_ConDescripcionConNumeros_DebeLanzarExcepcion() {
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("electronica");
        dto.setDescripcion("123");

        Categoria categoria = new Categoria();
        categoria.setNombre("electronica");
        categoria.setDescripcion("123");

        when(registrarCategoriaMapper.toEntity(any(RegistrarCategoriaDto.class))).thenReturn(categoria);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.registrarCategoria(dto);
        });

        assertEquals("La descripción no puede contener números.", exception.getMessage());
    }

    @Test
    void registrarCategoria_ConDescripcionValida_DebeRegistrarExitosamente() {
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("electronica");
        dto.setDescripcion("Productos y dispositivos electrónicos");

        Categoria categoria = new Categoria();
        categoria.setNombre("electronica");
        categoria.setDescripcion("Productos y dispositivos electrónicos");

        when(registrarCategoriaMapper.toEntity(any(RegistrarCategoriaDto.class))).thenReturn(categoria);
        when(categoriaRepository.existsByNombre(any())).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        when(registrarCategoriaMapper.toDto(any(Categoria.class))).thenReturn(dto);

        RegistrarCategoriaDto resultado = categoriaService.registrarCategoria(dto);

        assertNotNull(resultado);
        assertEquals("electronica", resultado.getNombre());
        assertEquals("Productos y dispositivos electrónicos", resultado.getDescripcion());
    }


}

