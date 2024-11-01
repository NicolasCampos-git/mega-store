package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RegistroUsuarioDtoTest {

    private RegistroUsuarioDto usuarioDto;

    @BeforeEach
    public void setUp() {
        usuarioDto = new RegistroUsuarioDto();
    }

    @Test
    public void testNombreVacio() {
        usuarioDto.setNombre("");//Nombre vacio
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        Assertions.assertFalse(usuarioDto.esValido(), "El nombre no debe estar vacio.");
    }

    @Test
    public void testNombreConEspacios() {
        usuarioDto.setNombre("Nombre con espacios");//Nombre con espacios en blanco
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        Assertions.assertFalse(usuarioDto.esValido(), "El nombre no debe contener espacios en blanco.");
    }

    @Test
    public void testNombreConMenosDeDosCaracteres() {
        usuarioDto.setNombre("J");//Nombre con menos de 2 caracteres
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        Assertions.assertFalse(usuarioDto.esValido(), "El nombre no debe contener menos de 2 caracteres.");
    }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        usuarioDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");//Nombre con mas de 64 caracteres
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        Assertions.assertFalse(usuarioDto.esValido(), "El nombre no debe contener mas de 64 caracteres.");
    }

    @Test
    public void testApellidoVacio() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("");//Apellido vacio
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        Assertions.assertFalse(usuarioDto.esValido(), "El apellido no debe estar vacio.");
    }

    @Test
    public void testApellidoConEspacios() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Apellido con espacios");//Apellido con espacios en blanco
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        Assertions.assertFalse(usuarioDto.esValido(), "El apellido no debe contener espacios en blanco.");
    }

    @Test
    public void testApellidoConMenosDeDosCaracteres() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("A");//Apellido con menos de 2 caracteres
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        Assertions.assertFalse(usuarioDto.esValido(), "El apellido no debe contener menos de 2 caracteres.");
    }

    @Test
    public void testApellidoConMasDeSesentaYCuatroCaracteres() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("EsteApellidoEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");//Apellido con mas de 64 caracteres
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        Assertions.assertFalse(usuarioDto.esValido(), "El apellido no debe contener mas de 64 caracteres.");
    }

    @Test
    public void testTelefonoVacio() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("");//Telefono vacio
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        Assertions.assertFalse(usuarioDto.esValido(), "El telefono no debe estar vacio.");
    }

    @Test
    public void testTelefonoConCaracteresDistintosANumeros() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("12345678a");//Telefono con caracteres distintos a numeros
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        Assertions.assertFalse(usuarioDto.esValido(), "El telefono no debe contener caracteres que no sean numeros.");
    }

    @Test
    public void testTelefonoConMenosDeNueveDigitos() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("12345678");//Telefono con menos de 9 digitos
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        Assertions.assertFalse(usuarioDto.esValido(), "El telefono no debe tener menos de 9 digitos.");
    }

    @Test
    public void testTelefonoConMasDeQuinceDigitos() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("1234567891234567");//Telefono con mas de 15 digitos
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        Assertions.assertFalse(usuarioDto.esValido(), "El telefono no debe tener mas de 15 digitos.");
    }

    @Test
    public void testEmailVacio() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("");//Email vacio
        usuarioDto.setContrasena("Test1Contraseña");  // Debe ser válido
        usuarioDto.setContrasenaRepetida("Test1Contraseña");  // Debe coincidir

        // Validación específica
        Assertions.assertFalse(usuarioDto.esValido(), "El email no debe estar vacio.");
    }

    @Test
    public void testEmailSinFormato() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez");//Email sin formato
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        // Validación específica
        Assertions.assertFalse(usuarioDto.esValido(), "El email no puede no tener formato de email.");
    }

    @Test
    public void testContrasenaVacia() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena(""); //Contraseña vacia
        usuarioDto.setContrasenaRepetida(""); //Contraseña vacia

        // Validación específica
        Assertions.assertFalse(usuarioDto.esValido(), "La contraseña no debe estar vacia.");
    }

    @Test
    public void testContrasenaConMenosDeOchoCaracteres() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("A1bcdef");//Contraseña con menos de 8 caracteres
        usuarioDto.setContrasenaRepetida("A1bcdef");//Contraseña con menos de 8 caracteres

        // Validación específica
        Assertions.assertFalse(usuarioDto.esValido(), "La contraseña no debe tener menos de 8 caracteres.");
    }

    @Test
    public void testContrasenaSinMayuscula() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("contrasena1");//Contraseña sin aunque sea una mayuscula
        usuarioDto.setContrasenaRepetida("contrasena1");//Contraseña sin aunque sea una mayuscula

        // Validación específica
        Assertions.assertFalse(usuarioDto.esValido(), "La contraseña necesita tener al menos una mayuscula.");
    }

    @Test
    public void testContrasenaSinNumero() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Contrasena");//Contraseña sin al menos un numero
        usuarioDto.setContrasenaRepetida("Contrasena");//Contraseña sin al menos un numero

        // Validación específica
        Assertions.assertFalse(usuarioDto.esValido(), "La contraseña necesita tener al menos un numero.");
    }


    @Test
    public void testContrasenaRepetidaVacia() {
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Contrasena1");
        usuarioDto.setContrasenaRepetida("");//Contraseñas no coinciden

        // Validación específica
        Assertions.assertFalse(usuarioDto.esValido(), "Las contraseñas deben coincidir.");
    }


    @Test
    public void testUsuarioValido() {
        //Datos Validos
        usuarioDto.setNombre("Juan");
        usuarioDto.setApellido("Pérez");
        usuarioDto.setTelefono("123456789");
        usuarioDto.setEmail("juan.perez@example.com");
        usuarioDto.setContrasena("Test1Contraseña");
        usuarioDto.setContrasenaRepetida("Test1Contraseña");

        // Validación específica
        assertTrue(usuarioDto.esValido(), "El usuario debería ser válido.");
    }

}
