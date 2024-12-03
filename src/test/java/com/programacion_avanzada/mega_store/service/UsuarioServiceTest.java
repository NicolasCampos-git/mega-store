package com.programacion_avanzada.mega_store.service;


import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.DTOs.RegistroUsuarioDto;
import com.programacion_avanzada.mega_store.DTOs.UsuarioDto;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;
import com.programacion_avanzada.mega_store.Service.ISenderService;
import com.programacion_avanzada.mega_store.Service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ISenderService senderService;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioDto usuarioDto;

    @BeforeEach
    public void setUp() {
        usuarioDto = new UsuarioDto();
        // No es necesario crear manualmente la instancia de categoriaService,
        // ya que Mockito se encarga de ello con @InjectMocks
    }

    @Test
    void testRegistrarUsuario() {
        // Crear el objeto DTO para el test
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Pérez");
        dto.setTelefono("123456789");
        dto.setEmail("juan.perez@example.com");
        dto.setContrasena("Contrasena1");
        dto.setContrasenaRepetida("Contrasena1");

        // Crear el usuario esperado después del mapeo
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setTelefono("123456789");
        usuario.setEmail("juan.perez@example.com");
        usuario.setContrasena("Contrasena1");

        // Configurar el comportamiento del mock
        when(usuarioRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Ejecutar el metodo
        RegistroUsuarioDto result = usuarioService.registrarUsuario(dto);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(dto.getNombre(), result.getNombre());
        assertEquals(dto.getApellido(), result.getApellido());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getTelefono(), result.getTelefono());

        // Verificar que los métodos del repositorio y el servicio de envío de correos fueron llamados
        verify(usuarioRepository).existsByEmail(dto.getEmail());
        verify(usuarioRepository).save(any(Usuario.class));
        verify(senderService).enviarCorreo(dto.getEmail());
    }

    @Test
    void testRegistrarUsuarioConContrasenasNoCoinciden() {
        // Crear el objeto DTO para el test
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Ana");
        dto.setApellido("López");
        dto.setTelefono("987654321");
        dto.setEmail("ana.lopez@example.com");
        dto.setContrasena("Contrasena1");
        dto.setContrasenaRepetida("OtraContrasena");

        // Ejecutar y verificar que se lance la excepción esperada
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.registrarUsuario(dto)
        );

        assertEquals("Las contrasenas no coinciden.", exception.getMessage());

        // Verificar que el metodo existsByEmail nunca fue llamado
        verify(usuarioRepository, never()).existsByEmail(anyString());
        // Verificar que no se llame al metodo save ni al servicio de envío de correos
        verify(usuarioRepository, never()).save(any());
        verify(senderService, never()).enviarCorreo(anyString());
    }

    @Test
    void testRegistrarUsuarioConCorreoYaRegistrado() {
        // Crear el objeto DTO para el test
        RegistroUsuarioDto dto = new RegistroUsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Pérez");
        dto.setTelefono("123456789");
        dto.setEmail("juan.perez@example.com");
        dto.setContrasena("Contrasena1");
        dto.setContrasenaRepetida("Contrasena1");

        // Simular que el correo ya existe en la base de datos
        when(usuarioRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        // Ejecutar y verificar que se lance la excepción esperada
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.registrarUsuario(dto)
        );

        assertEquals("El email ya está registrado.", exception.getMessage());

        // Verificar que el método existsByEmail fue llamado
        verify(usuarioRepository).existsByEmail(dto.getEmail());
        // Verificar que no se llame al método save ni al servicio de envío de correos
        verify(usuarioRepository, never()).save(any());
        verify(senderService, never()).enviarCorreo(anyString());
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
