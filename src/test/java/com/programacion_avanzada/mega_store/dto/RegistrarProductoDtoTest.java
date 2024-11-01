package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RegistrarProductoDtoTest {

    private RegistrarProductoDto productoDto;

    @BeforeEach
    public void setUp() {
        productoDto = new RegistrarProductoDto();
    }

    @Test
    public void testNombreConMenosDeDosCaracteres() {
        productoDto.setNombre("N");// Nombre de solo 1 carácter, debería ser inválido
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        // Validación específica
        Assertions.assertFalse(productoDto.esValido(), "El nombre no debe tener menos de 2 caracteres.");    }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        productoDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");// Nombre de mas de 64 carácteres, debería ser inválido
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        Assertions.assertFalse(productoDto.esValido(), "El nombre no debe tener más de 64 caracteres.");
    }

    @Test
    public void testNombreNoDebeContenerEspaciosEnBlanco() {
        productoDto.setNombre("Nombre Con Espacios"); // Nombre con espacios
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        Assertions.assertFalse(productoDto.esValido(), "El nombre no debe contener espacios en blanco.");
    }

    @Test
    public void testNombreConNumeros() {
        productoDto.setNombre("Nombre123"); // Nombre no válido por contener números
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        Assertions.assertFalse(productoDto.esValido(), "El nombre no debe contener números.");
    }

    @Test
    public void testNombreConCaracteresValidos() {
        productoDto.setNombre("NombreVálido");//Nombre con datos validos
        productoDto.setDescripcion("Descripcion válida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        assertTrue(productoDto.esValido(), "El nombre debería ser válido.");
    }

    @Test
    public void testNombreValido() {
        productoDto.setNombre("NombreValido");//Nombre valido
        productoDto.setDescripcion("Descripcion válida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        assertTrue(productoDto.esValido(), "El nombre debería ser válido.");
    }

    @Test
    public void testDescripcionConMenosDeDosCaracteres() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("D");//Descripcion de 1 caracter, deberia ser invalido
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        Assertions.assertFalse(productoDto.esValido(), "La descripcion no debe tener menos de 2 caracteres.");
    }

    @Test
    public void testDescripcionConMasDeCienCaracteres() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("EstaDescripcionEsDemasiadoLargaComoParaSerValidaYAExcedeLosCienCaracteresPorqueEstaCadenaEsExcesivaYNoDeberiaSerAceptada");//Descripcion de mas de 100 caracteres, deberia ser invalido
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        Assertions.assertFalse(productoDto.esValido(), "La descripcion no debe tener mas de 100 caracteres.");
    }

    @Test
    public void testDescripcionConNumeros() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripción con número 123"); // Descripción con números
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        Assertions.assertFalse(productoDto.esValido(), "La descripción no debe ser válida si contiene números.");
    }

    @Test
    public void testDescripcionConEspacios() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion con espacios");//Descripcion con espacios en blanco
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        assertTrue(productoDto.esValido(), "La descripción debería ser válida.");
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

        Assertions.assertFalse(productoDto.esValido(), "El tamaño no debe estar vacio.");
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

        Assertions.assertFalse(productoDto.esValido(), "El tamaño no debe tener mas de 10 caracteres.");
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

        Assertions.assertFalse(productoDto.esValido(), "El color no debe tener menos de 2 caracteres.");    }

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

        Assertions.assertFalse(productoDto.esValido(), "El color no debe tener mas de 5 caracteres.");    }

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

        Assertions.assertFalse(productoDto.esValido(), "El color no debe contener numeros.");    }

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

        Assertions.assertFalse(productoDto.esValido(), "El precio unitario no puede estar vacio.");    }

    @Test
    public void testPrecioUnitarioPositivo() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);//Precio unitario positivo
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        assertTrue(productoDto.esValido(), "El precio unitario debería ser válido."); }

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

        Assertions.assertFalse(productoDto.esValido(), "El stock no debe estar vacio.");    }

    @Test
    public void testStockPositivo() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);//Stock positivo
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        assertTrue(productoDto.esValido(), "El stock debería ser válido."); }

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

        Assertions.assertFalse(productoDto.esValido(), "El umbral bajo stock no puede estar vacio.");    }

    @Test
    public void testUmbralBajoStockPositivo() {
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);//Umbral bajo stock positivo
        productoDto.setMarcaId(1L);
        productoDto.setSubCategoriaId(2L);

        assertTrue(productoDto.esValido(), "El umbral bajo stock debería ser válido."); }


    @Test
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

        assertTrue(productoDto.esValido(), "La subcategoria debería ser válida."); }
}
