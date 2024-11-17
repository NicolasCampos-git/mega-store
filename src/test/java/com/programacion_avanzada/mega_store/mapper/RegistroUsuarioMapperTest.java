package com.programacion_avanzada.mega_store.mapper;

import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.Mapper.RegistroUsuarioMapper;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegistroUsuarioMapperTest {

    private final RegistroUsuarioMapper registroUsuarioMapper = new RegistroUsuarioMapper();

    @Test
    void testToEntity() {
        // Crear un objeto DTO de prueba
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("NombreValido");
        dto.setApellido("ApellidoValido");
        dto.setEmail("email@valido.com");
        dto.setTelefono("123456789");
        dto.setContrasena("Clave1");
        dto.setContrasenaRepetida("Clave1");

        // Usar el mapper para convertir el DTO en entidad
        Usuario usuario = registroUsuarioMapper.toEntity(dto);

        // Verificar que la marca no sea nula y que los valores sean correctos
        assertNotNull(usuario, "El usuario no debería ser nula");
        assertEquals("NombreValido", dto.getNombre());
        assertEquals("ApellidoValido", dto.getApellido());
        assertEquals("NombreValido", usuario.getNombre());
        assertEquals("ApellidoValido", usuario.getApellido());
        assertEquals("email@valido.com", usuario.getEmail());
        assertEquals("123456789", usuario.getTelefono());
        assertEquals("Clave1", usuario.getContrasena());
    }

    @Test
    void testToDto() {
        // Crear un objeto de entidad de prueba
        Usuario usuario = new Usuario();
        usuario.setNombre("NombreValido");
        usuario.setApellido("ApellidoValido");
        usuario.setEmail("email@valido.com");
        usuario.setTelefono("123456789");
        usuario.setContrasena("Clave1");

        // Usar el mapper para convertir la entidad en DTO
        RegistroUsuarioDto dto = registroUsuarioMapper.toDto(usuario);

        // Verificar que el DTO no sea nulo y que los valores sean correctos
        assertNotNull(dto, "El DTO no debería ser nulo");
        assertEquals("NombreValido", dto.getNombre());
        assertEquals("ApellidoValido", dto.getApellido());
        assertEquals("NombreValido", usuario.getNombre());
        assertEquals("ApellidoValido", usuario.getApellido());
        assertEquals("email@valido.com", usuario.getEmail());
        assertEquals("123456789", usuario.getTelefono());
        assertEquals("Clave1", usuario.getContrasena());
    }
}
