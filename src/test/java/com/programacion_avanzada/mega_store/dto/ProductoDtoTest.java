package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProductoDtoTest {

    private ProductoDto productoDto;

    @BeforeEach
    public void setUp() {
        productoDto = new ProductoDto();
    }

    @Test
    public void testNombreConMenosDeDosCaracteres() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("N");// Nombre de solo 1 carácter, debería ser inválido
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Validación específica
        Assertions.assertFalse(productoDto.esValido(), "El nombre no debe tener menos de 2 caracteres.");    }

    @Test
    public void testNombreConMasDeSesentaYCuatroCaracteres() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("EsteNombreEsDemasiadoLargoComoParaSerValidoYAExcedeLosSesentaYCuatroCaracteres");// Nombre de mas de 64 carácteres, debería ser inválido
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "El nombre no debe tener más de 64 caracteres.");
    }

    @Test
    public void testNombreNoDebeContenerEspaciosEnBlanco() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("Nombre Con Espacios"); // Nombre con espacios
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "El nombre no debe contener espacios en blanco.");
    }

    @Test
    public void testNombreConNumeros() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("Nombre123"); // Nombre no válido por contener números
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "El nombre no debe contener números.");
    }

    @Test
    public void testNombreConCaracteresValidos() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreVálido");//Nombre con datos validos
        productoDto.setDescripcion("Descripcion válida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        assertTrue(productoDto.esValido(), "El nombre debería ser válido.");
    }

    @Test
    public void testNombreValido() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");//Nombre valido
        productoDto.setDescripcion("Descripcion válida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        assertTrue(productoDto.esValido(), "El nombre debería ser válido.");
    }

    @Test
    public void testDescripcionConMenosDeDosCaracteres() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("D");//Descripcion de 1 caracter, deberia ser invalido
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "La descripcion no debe tener menos de 2 caracteres.");
    }

    @Test
    public void testDescripcionConMasDeCienCaracteres() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("EstaDescripcionEsDemasiadoLargaComoParaSerValidaYAExcedeLosCienCaracteresPorqueEstaCadenaEsExcesivaYNoDeberiaSerAceptada");//Descripcion de mas de 100 caracteres, deberia ser invalido
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "La descripcion no debe tener mas de 100 caracteres.");
    }

    @Test
    public void testDescripcionConNumeros() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripción con número 123"); // Descripción con números
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "La descripción no debe ser válida si contiene números.");
    }

    @Test
    public void testDescripcionConEspacios() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion con espacios");//Descripcion con espacios en blanco
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        assertTrue(productoDto.esValido(), "La descripción debería ser válida.");
    }

    @Test
    public void testTamanoVacio() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion con espacios");
        productoDto.setTamano("");//Tamaño vacio
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "El tamaño no debe estar vacio.");
    }

    @Test
    public void testTamanoConMasDeDiezCaracteres() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("HolaMundoaa");//Tamaño con mas de 10 caracteres
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "El tamaño no debe tener mas de 10 caracteres.");
    }

    @Test
    public void testColorConMenosDeDosCaracteres() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("A");//Color de 1 caracter,deberia ser invalido
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "El color no debe tener menos de 2 caracteres.");    }

    @Test
    public void testColorConMasDeCincoCaracteres() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Ejemplo");//Color de mas de 5 caracteres, deberia ser invalido
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "El color no debe tener mas de 5 caracteres.");    }

    @Test
    public void testColorConNumeros() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul1");//Color con numeros
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "El color no debe contener numeros.");    }

    @Test
    public void testPrecioUnitarioVacio() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(0.0);//Precio unitario vacio
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "El precio unitario no puede estar vacio.");    }

    @Test
    public void testPrecioUnitarioPositivo() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);//Precio unitario positivo
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        assertTrue(productoDto.esValido(), "El precio unitario debería ser válido."); }

    @Test
    public void testStockVacio() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(0);//Stock vacio
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "El stock no debe estar vacio.");    }

    @Test
    public void testStockPositivo() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);//Stock positivo
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        assertTrue(productoDto.esValido(), "El stock debería ser válido."); }

    @Test
    public void testUmbralBajoStockVacio() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(0);//Umbral bajo stock vacio
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "El umbral bajo stock no puede estar vacio.");    }

    @Test
    public void testUmbralBajoStockPositivo() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);//Umbral bajo stock positivo
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        assertTrue(productoDto.esValido(), "El umbral bajo stock debería ser válido."); }


    @Test
    public void testMarcaVacia() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);
        productoDto.setMarca(null);//Marca vacia o nula
        productoDto.setSubCategoria(subCategoriaDto);

        Assertions.assertFalse(productoDto.esValido(), "La marca no puede estar vacia.");    }

    @Test
    public void testMarcaPositiva() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);
        productoDto.setMarca(marcaDto);//Marca positiva
        productoDto.setSubCategoria(subCategoriaDto);

        assertTrue(productoDto.esValido(), "La marca debería ser válida."); }

    @Test
    public void testSubCategoriaVacia() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(null);//SubCategoria vacia o nula

        Assertions.assertFalse(productoDto.esValido(), "La subcategoria no puede estar vacia.");    }

    @Test
    public void testSubCategoriaPositiva() {
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("Grande");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);//SubCategoria positiva


        assertTrue(productoDto.esValido(), "La subcategoria debería ser válida."); }
}