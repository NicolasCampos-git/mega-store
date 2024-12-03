package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.UsuarioDto;
import com.programacion_avanzada.mega_store.Service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UsuarioDtoTest {

    private UsuarioDto usuarioDto;
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        // Instanciamos UsuarioService
        usuarioService = new UsuarioService();
        // Instanciamos UsuarioDto
        usuarioDto = new UsuarioDto();
    }

    @Test
    public void testValidarNombreVacio() {
        // Establecemos el nombre vacío
        usuarioDto.setNombre("");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("usuario");


        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarNombre(usuarioDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre no puede estar vacio.", exception.getMessage());
    }

    @Test
    public void testNombreConEspacios() {
        usuarioDto.setNombre("Nombre con espacios");//Nombre con espacios en blanco
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("usuario");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarNombre(usuarioDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre no debe contener espacios.", exception.getMessage());
    }

    @Test
    public void testNombreConMenosDeDosCaracteres() {
        usuarioDto.setNombre("J");//Nombre con menos de 2 caracteres
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("usuario");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarNombre(usuarioDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre debe tener entre 2 y 64 caracteres.", exception.getMessage());

    }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        usuarioDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");//Nombre con mas de 64 caracteres
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("usuario");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarNombre(usuarioDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testApellidoVacio() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("");//Apellido vacio
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("usuario");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarApellido(usuarioDto.getApellido()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El apellido no puede estar vacio.", exception.getMessage());
    }

    @Test
    public void testApellidoConEspacios() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Apellido con espacios");//Apellido con espacios en blanco
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("usuario");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarApellido(usuarioDto.getApellido()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El apellido no debe contener espacios.", exception.getMessage());
    }

    @Test
    public void testApellidoConMenosDeDosCaracteres() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("A");//Apellido con menos de 2 caracteres
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("usuario");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarApellido(usuarioDto.getApellido()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El apellido debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testApellidoConMasDeSesentaYCuatroCaracteres() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("EsteApellidoEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");//Apellido con mas de 64 caracteres
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("usuario");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarApellido(usuarioDto.getApellido()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El apellido debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testTelefonoVacio() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("");//Telefono vacio
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("usuario");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarTelefono(usuarioDto.getTelefono()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El telefono no puede estar vacio.", exception.getMessage());
    }

    @Test
    public void testTelefonoConCaracteresDistintosANumeros() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("12345678a");//Telefono con caracteres distintos a numeros
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("usuario");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarTelefono(usuarioDto.getTelefono()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El telefono solo debe contener numeros.", exception.getMessage());
    }

    @Test
    public void testTelefonoConMenosDeNueveDigitos() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("12345678");//Telefono con menos de 9 digitos
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("usuario");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarTelefono(usuarioDto.getTelefono()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El telefono debe tener entre 9 y 15 caracteres.", exception.getMessage());
    }

    @Test
    public void testTelefonoConMasDeQuinceDigitos() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("1234567891234567");//Telefono con mas de 15 digitos
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("usuario");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarTelefono(usuarioDto.getTelefono()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El telefono debe tener entre 9 y 15 caracteres.", exception.getMessage());
    }


    @Test
    public void testEmailVacio() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("");//Email vacio
        usuarioDto.setRol("usuario");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarEmail(usuarioDto.getEmail()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El email no puede estar vacio.", exception.getMessage());
    }

    @Test
    public void testEmailSinFormato() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez");//Email sin formato
        usuarioDto.setRol("usuario");


        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarEmail(usuarioDto.getEmail()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El email no es valido.", exception.getMessage());
    }

    @Test
    public void testUsuarioValido() {
        // Configuramos un usuario con datos válidos
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setRol("user");

        // No se debe lanzar ninguna excepción si todos los datos son válidos
        // Validamos cada uno de los campos del usuario
        usuarioService.valirdarNombre(usuarioDto.getNombre());
        usuarioService.valirdarApellido(usuarioDto.getApellido());
        usuarioService.valirdarTelefono(usuarioDto.getTelefono());
        usuarioService.valirdarEmail(usuarioDto.getEmail());

        // Si llegamos aquí, significa que las validaciones han pasado correctamente
    }
}