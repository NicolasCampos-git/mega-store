package com.programacion_avanzada.mega_store.integracion;

import com.programacion_avanzada.mega_store.DTOs.RegistrarCategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;
import com.programacion_avanzada.mega_store.Service.CategoriaService;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CategoriaServiceIntegrationTest {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaRepository categoriaRepository;


    @Test
    void testRegistrarCategoriaIntegration() {
        // Crear un DTO de prueba
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("CategoríaA");
        dto.setDescripcion("Descripción válida");

        // Ejecutar el metodo
        RegistrarCategoriaDto result = categoriaService.registrarCategoria(dto);

        // Verificar en la base de datos
        Optional<Categoria> categoriaGuardadaOptional = categoriaRepository.findByNombre("Categoríaa");
        assertTrue(categoriaGuardadaOptional.isPresent(), "La categoría debería estar presente en la base de datos");

        Categoria categoriaGuardada = categoriaGuardadaOptional.get();
        assertNotNull(categoriaGuardada);
        assertEquals("Categoríaa", categoriaGuardada.getNombre());
        assertEquals("descripción válida", categoriaGuardada.getDescripcion()); // Descripción normalizada
    }

    @Test
    void testRegistrarCategoriaConNombreExistente() {
        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setNombre("CategoríaA");
        categoriaExistente.setDescripcion("Descripción existente");
        categoriaRepository.save(categoriaExistente);

        // Crear un DTO de prueba para la nueva categoría con el mismo nombre
        RegistrarCategoriaDto dto = new RegistrarCategoriaDto();
        dto.setNombre("CategoríaA"); // Nombre ya existente
        dto.setDescripcion("Otra descripción");

        // Ejecutar el metodo y verificar que lanza una excepción
        Exception exception = assertThrows(EntityExistsException.class, () -> {
            categoriaService.registrarCategoria(dto); // Intentar registrar con nombre duplicado
        });

        // Verificar que la excepción tenga el mensaje esperado
        assertEquals("La categoria ya existe", exception.getMessage());

        // Verificar que no se haya creado una nueva categoría en la base de datos
        long count = categoriaRepository.count();
        assertEquals(1, count, "No debería haber más de una categoría en la base de datos");
    }


    @Test
    void testEliminarCategoria() {
        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(1L);
        categoriaExistente.setNombre("CategoriaA");
        categoriaExistente.setDescripcion("Descripción de la categoría");

        categoriaRepository.save(categoriaExistente);

        categoriaService.eliminar(1L); // Metodo hipotético que implementa la eliminación lógica

        Optional<Categoria> categoriaEliminadaOptional = categoriaRepository.findById(1L);
        assertTrue(categoriaEliminadaOptional.isPresent(), "La categoría debería existir en la base de datos");

        Categoria categoriaEliminada = categoriaEliminadaOptional.get();
        assertFalse(categoriaEliminada.isEstaActivo(), "La categoría debe estar marcada como eliminada lógicamente");
    }

    @Test
    void testEliminarCategoriaInexistente() {
        long categoriaIdInexistente = 99L; // ID de la categoría que no existe

        // Verificar que la categoría no existe antes de intentar eliminarla
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(categoriaIdInexistente);
        assertFalse(categoriaOptional.isPresent(), "La categoría no debería existir en la base de datos");

        // Ejecutar el metodo eliminar para una categoría inexistente
        categoriaService.eliminar(categoriaIdInexistente);

        // También verificar que la categoría no fue marcada como eliminada
        categoriaOptional = categoriaRepository.findById(categoriaIdInexistente);
        assertFalse(categoriaOptional.isPresent(), "La categoría no debería existir en la base de datos");
    }

}
