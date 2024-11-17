package com.programacion_avanzada.mega_store.service;

import com.programacion_avanzada.mega_store.DTOs.RegistrarCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.RegistrarCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;
import com.programacion_avanzada.mega_store.Service.CategoriaService;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private RegistrarCategoriaMapper registrarCategoriaMapper;

    @InjectMocks
    private CategoriaService categoriaService;


    @Test
    void testRegistrarCategoria() {
        // Crear un objeto DTO de prueba
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("CategoriaValida");
        dto.setDescripcion("Descripción válida");

        // Configurar el comportamiento del mock para que devuelva una nueva Categoria
        Categoria categotiaEsperada = new Categoria();
        categotiaEsperada.setNombre("CategoriaValida");
        categotiaEsperada.setDescripcion("Descripción válida");
        when(registrarCategoriaMapper.toEntity(dto)).thenReturn(categotiaEsperada);

        //Ahora llama al metodo despues de haber configurado el mock
        Categoria categoria = registrarCategoriaMapper.toEntity(dto);

        // Simular el comportamiento del repositorio
        when(categoriaRepository.existsByNombre(anyString())).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        when(registrarCategoriaMapper.toDto(categoria)).thenReturn(dto);

        // Ejecutar el metodo a probar
        RegistrarCategoriaDto result = categoriaService.registrarCategoria(dto);

        // Verificar los resultados
        assertNotNull(result, "El resultado no debería ser nulo");
        assertEquals("CategoriaValida", result.getNombre());
        assertEquals("Descripción válida", result.getDescripcion());

        // Verificar que se hayan llamado los métodos esperados
        verify(categoriaRepository).existsByNombre("CategoriaValida");
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void testRegistrarCategoriaYaExistente() {
        // Crear un objeto DTO de prueba
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("CategoriaExistente");
        dto.setDescripcion("Descripción existente");

        // Configurar el comportamiento del mock para que devuelva una nueva Categoria
        Categoria categoriaEsperada = new Categoria();
        categoriaEsperada.setNombre("CategoriaValida");
        categoriaEsperada.setDescripcion("Descripción válida");
        when(registrarCategoriaMapper.toEntity(dto)).thenReturn(categoriaEsperada);

        // Simular que la categoria ya existe en el repositorio
        when(categoriaRepository.existsByNombre(anyString())).thenReturn(true);

        // Ejecutar el método a probar y verificar que se lanza la excepción
        Exception exception = assertThrows(EntityExistsException.class, () -> {
            categoriaService.registrarCategoria(dto);
        });

        assertEquals("La categoria ya existe", exception.getMessage());
    }
}
