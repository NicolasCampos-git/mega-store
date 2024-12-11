package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.Service.ProductoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegistrarProductoDtoTest {

    private RegistrarProductoDto productoDto;
    private ProductoService productoService;

    @BeforeEach
    public void setUp() {
        productoDto = new RegistrarProductoDto();
        productoService = new ProductoService();
    }

    @Test
    public void testNombreConMenosDeDosCaracteres() {
        productoDto.setNombre("N");// Nombre de solo 1 carácter, debería ser inválido
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarNombre(productoDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre del producto debe tener entre 2 y 64 caracteres.", exception.getMessage());    }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        productoDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");// Nombre de mas de 64 carácteres, debería ser inválido
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarNombre(productoDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre del producto debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testNombreNoDebeContenerEspaciosEnBlanco() {
        productoDto.setNombre("Nombre Con Espacios"); // Nombre con espacios
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarNombre(productoDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre del producto no debe contener espacios.", exception.getMessage());
    }

    @Test
    public void testNombreConNumeros() {
        productoDto.setNombre("Nombre123"); // Nombre no válido por contener números
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarNombre(productoDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre no debe contener números.", exception.getMessage());
    }

    @Test
    public void testNombreConCaracteresValidos() {
        productoDto.setNombre("NombreVálido");//Nombre con datos validos
        productoDto.setDescripcion("Descripcion válida");
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // No se debe lanzar ninguna excepción si todos los datos son válidos
        // Validamos cada uno de los campos del usuario
        productoService.validarNombre(productoDto.getNombre());
        productoService.validarDescripcion(productoDto.getDescripcion());
        productoService.validarTamano(productoDto.getTamano());
        productoService.validarColor(productoDto.getColor());
        productoService.valirdarPrecio(productoDto.getPrecioUnitario());
        productoService.validarStock(productoDto.getStock());
        productoService.validarUmbralBajoStock(productoDto.getUmbralBajoStock());
    }

    @Test
    public void testNombreValido() {
        productoDto.setNombre("NombreValido");//Nombre valido
        productoDto.setDescripcion("Descripcion válida");
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // No se debe lanzar ninguna excepción si todos los datos son válidos
        // Validamos cada uno de los campos del usuario
        productoService.validarNombre(productoDto.getNombre());
        productoService.validarDescripcion(productoDto.getDescripcion());
        productoService.validarTamano(productoDto.getTamano());
        productoService.validarColor(productoDto.getColor());
        productoService.valirdarPrecio(productoDto.getPrecioUnitario());
        productoService.validarStock(productoDto.getStock());
        productoService.validarUmbralBajoStock(productoDto.getUmbralBajoStock());
    }

    @Test
    public void testDescripcionConMenosDeDosCaracteres() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("D");//Descripcion de 1 caracter, deberia ser invalido
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarDescripcion(productoDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion del producto debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testDescripcionConMasDeCienCaracteres() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("EstaDescripcionEsDemasiadoLargaComoParaSerValidaYAExcedeLosCienCaracteresPorqueEstaCadenaEsExcesivaYNoDeberiaSerAceptada");//Descripcion de mas de 100 caracteres, deberia ser invalido
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarDescripcion(productoDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion del producto debe tener entre 2 y 64 caracteres.", exception.getMessage());
    }

    @Test
    public void testDescripcionConNumeros() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripción con número 123"); // Descripción con números
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarDescripcion(productoDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion no debe contener números.", exception.getMessage());
    }

    @Test
    public void testDescripcionConEspacios() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion con espacios");//Descripcion con espacios en blanco
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // No se debe lanzar ninguna excepción si todos los datos son válidos
        // Validamos cada uno de los campos del usuario
        productoService.validarNombre(productoDto.getNombre());
        productoService.validarDescripcion(productoDto.getDescripcion());
        productoService.validarTamano(productoDto.getTamano());
        productoService.validarColor(productoDto.getColor());
        productoService.valirdarPrecio(productoDto.getPrecioUnitario());
        productoService.validarStock(productoDto.getStock());
        productoService.validarUmbralBajoStock(productoDto.getUmbralBajoStock());
    }

    @Test
    public void testTamanoVacio() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion con espacios");
        productoDto.setTamano("");//Tamaño vacio
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarTamano(productoDto.getTamano()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El tamaño del producto no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testTamanoConMasDeDiezCaracteres() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("HolaMundoaa");//Tamaño con mas de 10 caracteres
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarTamano(productoDto.getTamano()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("Solo se permite XS,S,M,L, XL O XXL para el tamaño del producto.", exception.getMessage());
    }

    @Test
    public void testColorConMenosDeDosCaracteres() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("A");//Color de 1 caracter,deberia ser invalido
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarColor(productoDto.getColor()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El color del producto debe tener entre 2 y 5 caracteres.", exception.getMessage());    }

    @Test
    public void testColorConMasDeCincoCaracteres() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Ejemplo");//Color de mas de 5 caracteres, deberia ser invalido
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarColor(productoDto.getColor()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El color del producto debe tener entre 2 y 5 caracteres.", exception.getMessage());    }

    @Test
    public void testColorConNumeros() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul1");//Color con numeros
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarColor(productoDto.getColor()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El color no debe contener números.", exception.getMessage());     }

    @Test
    public void testPrecioUnitarioVacio() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(0.0);//Precio unitario vacio
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.valirdarPrecio(productoDto.getPrecioUnitario()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El precio unitario del producto no puede estar vacío.", exception.getMessage());    }

    @Test
    public void testPrecioUnitarioPositivo() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);//Precio unitario positivo
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // No se debe lanzar ninguna excepción si todos los datos son válidos
        // Validamos cada uno de los campos del usuario
        productoService.validarNombre(productoDto.getNombre());
        productoService.validarDescripcion(productoDto.getDescripcion());
        productoService.validarTamano(productoDto.getTamano());
        productoService.validarColor(productoDto.getColor());
        productoService.valirdarPrecio(productoDto.getPrecioUnitario());
        productoService.validarStock(productoDto.getStock());
        productoService.validarUmbralBajoStock(productoDto.getUmbralBajoStock()); }

    @Test
    public void testStockVacio() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(0);//Stock vacio
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarStock(productoDto.getStock()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El stock del producto no puede estar vacío.", exception.getMessage());    }

    @Test
    public void testStockPositivo() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);//Stock positivo
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // No se debe lanzar ninguna excepción si todos los datos son válidos
        // Validamos cada uno de los campos del usuario
        productoService.validarNombre(productoDto.getNombre());
        productoService.validarDescripcion(productoDto.getDescripcion());
        productoService.validarTamano(productoDto.getTamano());
        productoService.validarColor(productoDto.getColor());
        productoService.valirdarPrecio(productoDto.getPrecioUnitario());
        productoService.validarStock(productoDto.getStock());
        productoService.validarUmbralBajoStock(productoDto.getUmbralBajoStock()); }

    @Test
    public void testUmbralBajoStockVacio() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(0);//Umbral bajo stock vacio
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarUmbralBajoStock(productoDto.getUmbralBajoStock()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El umbral bajo de stock del producto no puede estar vacío.", exception.getMessage());    }

    @Test
    public void testUmbralBajoStockPositivo() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);//Umbral bajo stock positivo
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // No se debe lanzar ninguna excepción si todos los datos son válidos
        // Validamos cada uno de los campos del usuario
        productoService.validarNombre(productoDto.getNombre());
        productoService.validarDescripcion(productoDto.getDescripcion());
        productoService.validarTamano(productoDto.getTamano());
        productoService.validarColor(productoDto.getColor());
        productoService.valirdarPrecio(productoDto.getPrecioUnitario());
        productoService.validarStock(productoDto.getStock());
        productoService.validarUmbralBajoStock(productoDto.getUmbralBajoStock()); }


    /*@Test
    public void testMarcaVacia() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);
        productoDto.setMarcaId(null);//Marca vacia o nula
        productoDto.setSubCategoriaId(2L);

        Assertions.assertFalse(productoDto.esValido(), "La marca no puede estar vacia.");    }

    @Test
    public void testMarcaPositiva() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);
        productoDto.setMarcaId(1L);//Marca positiva
        productoDto.setSubCategoriaId(2L);

        assertTrue(productoDto.esValido(), "La marca debería ser válida."); }

    @Test
    public void testSubCategoriaVacia() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(null);//SubCategoria vacia o nula

        Assertions.assertFalse(productoDto.esValido(), "La subcategoria no puede estar vacia.");    }

    @Test
    public void testSubCategoriaPositiva() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);//SubCategoria positiva

        assertTrue(productoDto.esValido(), "La subcategoria debería ser válida."); }*/
}
