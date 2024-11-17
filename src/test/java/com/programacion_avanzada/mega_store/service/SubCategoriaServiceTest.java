package com.programacion_avanzada.mega_store.service;

import com.programacion_avanzada.mega_store.DTOs.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.RegistrarSubCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;
import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;
import com.programacion_avanzada.mega_store.Service.SubCategoriaService;
import jakarta.persistence.EntityExistsException;
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
}


