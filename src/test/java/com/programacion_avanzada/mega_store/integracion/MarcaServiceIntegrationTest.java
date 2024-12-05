package com.programacion_avanzada.mega_store.integracion;

import com.programacion_avanzada.mega_store.DTOs.RegistrarCategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarMarcaDto;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;
import com.programacion_avanzada.mega_store.Service.CategoriaService;
import com.programacion_avanzada.mega_store.Service.MarcaService;
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
import static org.junit.jupiter.api.Assertions.assertFalse;

@Transactional
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MarcaServiceIntegrationTest {

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private MarcaRepository marcaRepository;


    @Test
    void testRegistrarMarcaIntegration() {
        // Crear un DTO de prueba
        RegistrarMarcaDto dto = new RegistrarMarcaDto();
        dto.setNombre("Marca");
        dto.setDescripcion("Descripción válida");

        // Ejecutar el metodo
        RegistrarMarcaDto result = marcaService.registrarMarca(dto);

        // Verificar en la base de datos
        Optional<Marca> marcaGuardadaOptional = marcaRepository.findByNombre("Marca");
        assertTrue(marcaGuardadaOptional.isPresent(), "La marca debería estar presente en la base de datos");

        Marca marcaGuardada = marcaGuardadaOptional.get();
        assertNotNull(marcaGuardada);
        assertEquals("Marca", marcaGuardada.getNombre());
        assertEquals("descripción válida", marcaGuardada.getDescripcion()); // Descripción normalizada
    }


    @Test
    void testEliminarMarca() {
        Marca marcaExistente = new Marca();
        marcaExistente.setId(1L);
        marcaExistente.setNombre("Marca");
        marcaExistente.setDescripcion("Descripción de la categoría");

        marcaRepository.save(marcaExistente);

        marcaService.eliminar(1L); // Metodo hipotético que implementa la eliminación lógica

        Optional<Marca> marcaEliminadaOptional = marcaRepository.findById(1L);
        assertTrue(marcaEliminadaOptional.isPresent(), "La marca debería existir en la base de datos");

        Marca marcaEliminada = marcaEliminadaOptional.get();
        assertFalse(marcaEliminada.isEstaActivo(), "La marca debe estar marcada como eliminada lógicamente");
    }

    @Test
    void testEliminarCategoriaInexistente() {
        long marcaIdInexistente = 99L; // ID de la marca que no existe

        // Verificar que la marca no existe antes de intentar eliminarla
        Optional<Marca> marcaOptional = marcaRepository.findById(marcaIdInexistente);
        assertFalse(marcaOptional.isPresent(), "La marca no debería existir en la base de datos");

        // Ejecutar el metodo eliminar para una marca inexistente
        marcaService.eliminar(marcaIdInexistente);

        // También verificar que la marca no fue marcada como eliminada
        marcaOptional = marcaRepository.findById(marcaIdInexistente);
        assertFalse(marcaOptional.isPresent(), "La marca no debería existir en la base de datos");
    }

}
