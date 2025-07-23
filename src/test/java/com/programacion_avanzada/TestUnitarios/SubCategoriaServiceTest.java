package com.programacion_avanzada.TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;


import com.programacion_avanzada.mega_store.DTOs.SubcategoriaDtos.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.SubcategoriaMappers.RegistrarSubCategoriaMapper;
import com.programacion_avanzada.mega_store.Mapper.SubcategoriaMappers.SubCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;
import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;
import com.programacion_avanzada.mega_store.Service.SubCategoriaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SubCategoriaServiceTest {

    @Mock
    private SubCategoriaRepository subCategoriaRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private SubCategoriaMapper subCategoriaMapper;

    @Mock
    private RegistrarSubCategoriaMapper registrarSubCategoriaMapper;

    @InjectMocks
    private SubCategoriaService subCategoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarSubCategoria_ConDatosValidos_DeberiaRegistrarExitosamente() {
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("hogar");
        dto.setDescripcion("productos para el hogar");
        dto.setCategoriaId(1L);

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNombre("hogar");
        subCategoria.setDescripcion("productos para el hogar");

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        subCategoria.setCategoria(categoria);

        when(categoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsByNombre("hogar")).thenReturn(false);
        when(registrarSubCategoriaMapper.toEntity(dto)).thenReturn(subCategoria);
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(subCategoriaRepository.save(any(SubCategoria.class))).thenReturn(subCategoria);

        SubCategoria resultado = subCategoriaService.registrarSubCategoria(dto);

        assertNotNull(resultado);
        assertEquals("Hogar", resultado.getNombre()); // por normalización
        assertEquals("productos para el hogar", resultado.getDescripcion());
    }

    @Test
    void registrarSubCategoria_CategoriaInexistente_DeberiaLanzarExcepcion() {
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("hogar");
        dto.setDescripcion("productos");
        dto.setCategoriaId(99L);

        when(categoriaRepository.existsById(99L)).thenReturn(false);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.registrarSubCategoria(dto);
        });

        assertEquals("La categoría no existe", ex.getMessage());
    }

    @Test
    void registrarSubCategoria_ConNombreExistente_DeberiaLanzarExcepcion() {
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("hogar");
        dto.setDescripcion("productos");
        dto.setCategoriaId(1L);

        when(categoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsByNombre("hogar")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.registrarSubCategoria(dto);
        });

        assertEquals("La subcategoria ya existe", ex.getMessage());
    }

    @Test
    void registrarSubCategoria_ConNombreNulo_DeberiaLanzarExcepcion() {
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("");
        dto.setDescripcion("productos");
        dto.setCategoriaId(1L);

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNombre("");
        subCategoria.setDescripcion("productos");

        when(categoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsByNombre("")).thenReturn(false);
        when(registrarSubCategoriaMapper.toEntity(dto)).thenReturn(subCategoria);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.registrarSubCategoria(dto);
        });

        assertEquals("El nombre no puede estar vacio.", ex.getMessage());
    }

    @Test
    void registrarSubCategoria_ConNombreDeLongitudMenor_DeberiaLanzarExcepcion() {
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("a");
        dto.setDescripcion("productos");
        dto.setCategoriaId(1L);

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNombre("a");
        subCategoria.setDescripcion("productos");

        when(categoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsByNombre("a")).thenReturn(false);
        when(registrarSubCategoriaMapper.toEntity(dto)).thenReturn(subCategoria);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.registrarSubCategoria(dto);
        });

        assertEquals("El nombre debe tener entre 2 y 64 caracteres.", ex.getMessage());
    }

    @Test
    void registrarSubCategoria_ConNombreDeLongitudMayor_DeberiaLanzarExcepcion() {
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("Accesoriosyherramientasespecializadasparacarpinteríaartesanal");
        dto.setDescripcion("productos");
        dto.setCategoriaId(1L);

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNombre("Accesorios y herramientas especializadas para carpintería artesanal");
        subCategoria.setDescripcion("productos");

        when(categoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsByNombre("Accesorios y herramientas especializadas para carpintería artesanal")).thenReturn(false);
        when(registrarSubCategoriaMapper.toEntity(dto)).thenReturn(subCategoria);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.registrarSubCategoria(dto);
        });

        assertEquals("El nombre debe tener entre 2 y 64 caracteres.", ex.getMessage());
    }

    @Test
    void registrarSubCategoria_ConNombreConEspacios_DeberiaLanzarExcepcion() {
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("hog ar");
        dto.setDescripcion("productos");
        dto.setCategoriaId(1L);

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNombre("hog ar");
        subCategoria.setDescripcion("productos");

        when(categoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsByNombre("hog ar")).thenReturn(false);
        when(registrarSubCategoriaMapper.toEntity(dto)).thenReturn(subCategoria);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.registrarSubCategoria(dto);
        });

        assertEquals("El nombre no debe contener espacios.", ex.getMessage());
    }

    @Test
    void registrarSubCategoria_ConNombreConNumeros_DeberiaLanzarExcepcion() {
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("a1");
        dto.setDescripcion("productos");
        dto.setCategoriaId(1L);

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNombre("a1");
        subCategoria.setDescripcion("productos");

        when(categoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsByNombre("a1")).thenReturn(false);
        when(registrarSubCategoriaMapper.toEntity(dto)).thenReturn(subCategoria);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.registrarSubCategoria(dto);
        });

        assertEquals("El nombre no debe contener números.", ex.getMessage());
    }

    @Test
    void registrarSubCategoria_ConDescripcionVacia_DeberiaLanzarExcepcion() {
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("hogar");
        dto.setDescripcion("");
        dto.setCategoriaId(1L);

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNombre("hogar");
        subCategoria.setDescripcion("");

        when(categoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsByNombre("hogar")).thenReturn(false);
        when(registrarSubCategoriaMapper.toEntity(dto)).thenReturn(subCategoria);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.registrarSubCategoria(dto);
        });

        assertEquals("La descripcion no puede estar vacia.", ex.getMessage());
    }

    @Test
    void registrarSubCategoria_ConDescripcionDeLongitudMenor_DeberiaLanzarExcepcion() {
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("hogar");
        dto.setDescripcion("a");
        dto.setCategoriaId(1L);

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNombre("hogar");
        subCategoria.setDescripcion("a");

        when(categoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsByNombre("hogar")).thenReturn(false);
        when(registrarSubCategoriaMapper.toEntity(dto)).thenReturn(subCategoria);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.registrarSubCategoria(dto);
        });

        assertEquals("La descripcion debe tener entre 2 y 64 caracteres.", ex.getMessage());
    }

    @Test
    void registrarSubCategoria_ConDescripcionDeLongitudMayor_DeberiaLanzarExcepcion() {
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("hogar");
        dto.setDescripcion("Esta subcategoría incluye herramientas específicas utilizadas en procesos detallados de carpintería artesanal y diseño de interiores.");
        dto.setCategoriaId(1L);

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNombre("hogar");
        subCategoria.setDescripcion("Esta subcategoría incluye herramientas específicas utilizadas en procesos detallados de carpintería artesanal y diseño de interiores.");

        when(categoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsByNombre("hogar")).thenReturn(false);
        when(registrarSubCategoriaMapper.toEntity(dto)).thenReturn(subCategoria);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.registrarSubCategoria(dto);
        });

        assertEquals("La descripcion debe tener entre 2 y 64 caracteres.", ex.getMessage());
    }

    @Test
    void registrarSubCategoria_ConDescripcionConNumeros_DeberiaLanzarExcepcion() {
        RegistrarSubCategoriaDto dto = new RegistrarSubCategoriaDto();
        dto.setNombre("hogar");
        dto.setDescripcion("productos123");
        dto.setCategoriaId(1L);

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNombre("hogar");
        subCategoria.setDescripcion("productos123");

        when(categoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsByNombre("hogar")).thenReturn(false);
        when(registrarSubCategoriaMapper.toEntity(dto)).thenReturn(subCategoria);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.registrarSubCategoria(dto);
        });

        assertEquals("La descripcion no debe contener números.", ex.getMessage());
    }

    @Test
    void eliminarSubCategoria_ExistenteYActiva_DeberiaDesactivarla() {
        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setId(1L);
        subCategoria.setEstaActivo(true);

        when(subCategoriaRepository.findById(1L)).thenReturn(Optional.of(subCategoria));
        when(subCategoriaRepository.save(any(SubCategoria.class))).thenReturn(subCategoria);

        subCategoriaService.eliminar(1L);

        assertFalse(subCategoria.isEstaActivo());
        verify(subCategoriaRepository).save(subCategoria);
    }

    @Test
    void eliminarSubCategoria_Inexistente_DeberiaLanzarExcepcion() {
        when(subCategoriaRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.eliminar(1L);
        });

        assertEquals("La subcategoria no existe o ya esta inactiva.", ex.getMessage());
    }

    @Test
    void reactivarSubCategoria_ExistenteInactiva_DeberiaActivarla() {
        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setId(1L);
        subCategoria.setEstaActivo(false);

        when(subCategoriaRepository.findById(1L)).thenReturn(Optional.of(subCategoria));
        when(subCategoriaRepository.save(any(SubCategoria.class))).thenReturn(subCategoria);

        subCategoriaService.reactivar(1L);

        assertTrue(subCategoria.isEstaActivo());
        verify(subCategoriaRepository).save(subCategoria);
    }

    @Test
    void reactivarSubCategoria_YaActiva_DeberiaLanzarExcepcion() {
        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setId(1L);
        subCategoria.setEstaActivo(true);

        when(subCategoriaRepository.findById(1L)).thenReturn(Optional.of(subCategoria));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            subCategoriaService.reactivar(1L);
        });

        assertEquals("La subcategoria no existe o ya esta activa.", ex.getMessage());
    }
}

