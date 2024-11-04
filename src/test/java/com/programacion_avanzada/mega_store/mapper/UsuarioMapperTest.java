package com.programacion_avanzada.mega_store.mapper;

import com.programacion_avanzada.mega_store.DTOs.UsuarioDto;
import com.programacion_avanzada.mega_store.Mapper.UsuarioMapper;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioMapperTest {

    private final UsuarioMapper usuarioMapper = new UsuarioMapper();

    @Test
    void testToEntity() {
        // Crear un objeto DTO de prueba con todos los campos
        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("NombreValido");
        dto.setApellido("ApellidoValido");
        dto.setEmail("email@valido.com");
        dto.setTelefono("123456789");
        dto.setRol("Usuario");

        // Usar el mapper para convertir el DTO en entidad
        Usuario usuario = usuarioMapper.toEntity(dto);

        // Verificar que el objeto Usuario no sea nulo y que los valores sean correctos
        assertNotNull(usuario, "El usuario no debería ser nulo");
        assertEquals("NombreValido", usuario.getNombre());
        assertEquals("ApellidoValido", usuario.getApellido());
        assertEquals("email@valido.com", usuario.getEmail());
        assertEquals("123456789", usuario.getTelefono());
        assertEquals("Usuario", usuario.getRol());
        assertFalse(usuario.isEstaActivo(), "Por defecto, el campo estaActivo debería ser falso");
    }

    @Test
    void testToDto() {
        // Crear un objeto Usuario de prueba con todos los campos
        Usuario usuario = new Usuario();
        usuario.setNombre("NombreValido");
        usuario.setApellido("ApellidoValido");
        usuario.setEmail("email@valido.com");
        usuario.setTelefono("123456789");
        usuario.setRol("Usuario");
        usuario.setEstaActivo(true);

        // Usar el mapper para convertir la entidad en DTO
        UsuarioDto dto = usuarioMapper.toDto(usuario);

        // Verificar que el DTO no sea nulo y que los valores sean correctos
        assertNotNull(dto, "El DTO no debería ser nulo");
        assertEquals("NombreValido", dto.getNombre());
        assertEquals("ApellidoValido", dto.getApellido());
        assertEquals("email@valido.com", dto.getEmail());
        assertEquals("123456789", dto.getTelefono());
        assertEquals("Usuario", dto.getRol());
    }
}
