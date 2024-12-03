package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.Service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegistroUsuarioDtoTest {

    private RegistroUsuarioDto usuarioDto;
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        usuarioDto = new RegistroUsuarioDto();
        usuarioService = new UsuarioService();
    }

    @Test
    public void testNombreVacio() {
        // Establecemos el nombre vacío
        usuarioDto.setNombre("");//Nombre vacio
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");


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
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

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
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

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
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

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
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

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
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

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
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

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
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

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
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

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
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

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
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

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
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

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
        usuarioDto.setContrasena("Test1Contraseña");  // Debe ser válido
        usuarioDto.setContrasenaRepetida("Test1Contraseña");  // Debe coincidir

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
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarEmail(usuarioDto.getEmail()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El email no es valido.", exception.getMessage());
    }

    @Test
    public void testContrasenaVacia() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena(""); //Contraseña vacia
        usuarioDto.setContrasenaRepetida(""); //Contraseña vacia

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarContrasena(usuarioDto.getContrasena(), usuarioDto.getContrasenaRepetida()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La contrasena no puede estar vacia.", exception.getMessage());
    }

    @Test
    public void testContrasenaConMenosDeOchoCaracteres() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("A1bcdef");//Contraseña con menos de 8 caracteres
        usuarioDto.setContrasenaRepetida("A1bcdef");//Contraseña con menos de 8 caracteres

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarContrasena(usuarioDto.getContrasena(), usuarioDto.getContrasenaRepetida()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La contrasena debe tener al menos 8 caracteres.", exception.getMessage());
    }

    @Test
    public void testContrasenaSinMayuscula() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("contrasena1");//Contraseña sin aunque sea una mayuscula
        usuarioDto.setContrasenaRepetida("contrasena1");//Contraseña sin aunque sea una mayuscula

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarContrasena(usuarioDto.getContrasena(), usuarioDto.getContrasenaRepetida()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La contrasena debe tener al menos una mayuscula y un numero.", exception.getMessage());
    }

    @Test
    public void testContrasenaSinNumero() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Contrasena");//Contraseña sin al menos un numero
        usuarioDto.setContrasenaRepetida("Contrasena");//Contraseña sin al menos un numero

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarContrasena(usuarioDto.getContrasena(), usuarioDto.getContrasenaRepetida()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La contrasena debe tener al menos una mayuscula y un numero.", exception.getMessage());
    }


    @Test
    public void testContrasenaRepetidaVacia() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Contrasena1");
        usuarioDto.setContrasenaRepetida("");//Contraseñas no coinciden

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.valirdarContrasena(usuarioDto.getContrasena(), usuarioDto.getContrasenaRepetida()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("Las contrasenas no coinciden.", exception.getMessage());
    }


    @Test
    public void testUsuarioValido() {
        //Datos Validos
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Contrasena1");
        usuarioDto.setContrasenaRepetida("Contrasena1");

        // No se debe lanzar ninguna excepción si todos los datos son válidos
        // Validamos cada uno de los campos del usuario
        usuarioService.valirdarNombre(usuarioDto.getNombre());
        usuarioService.valirdarApellido(usuarioDto.getApellido());
        usuarioService.valirdarTelefono(usuarioDto.getTelefono());
        usuarioService.valirdarEmail(usuarioDto.getEmail());
        usuarioService.valirdarContrasena(usuarioDto.getContrasena(), usuarioDto.getContrasenaRepetida());

        // Si llegamos aquí, significa que las validaciones han pasado correctamente
    }

}
