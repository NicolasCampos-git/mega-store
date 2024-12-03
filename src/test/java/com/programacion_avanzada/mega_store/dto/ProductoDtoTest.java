package com.programacion_avanzada.mega_store.dto;

import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;

import com.programacion_avanzada.mega_store.Mapper.ProductoMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;
import com.programacion_avanzada.mega_store.Service.ProductoService;
import com.programacion_avanzada.mega_store.Service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductoDtoTest {

    private ProductoDto productoDto;
    private ProductoService productoService;

    @BeforeEach
    public void setUp() {
        productoDto = new ProductoDto();
        productoService = new ProductoService();
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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarNombre(productoDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre del producto debe tener entre 2 y 64 caracteres.", exception.getMessage());   }

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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarNombre(productoDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre del producto debe tener entre 2 y 64 caracteres.", exception.getMessage());
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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarNombre(productoDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre del producto no debe contener espacios.", exception.getMessage());
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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarNombre(productoDto.getNombre()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El nombre no debe contener números.", exception.getMessage());
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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

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
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");//Nombre valido
        productoDto.setDescripcion("Descripcion válida");
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

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
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("D");//Descripcion de 1 caracter, deberia ser invalido
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarDescripcion(productoDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion del producto debe tener entre 2 y 64 caracteres.", exception.getMessage());
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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarDescripcion(productoDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion del producto debe tener entre 2 y 64 caracteres.", exception.getMessage());
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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarDescripcion(productoDto.getDescripcion()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("La descripcion no debe contener números.", exception.getMessage());
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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

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

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarTamano(productoDto.getTamano()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El tamaño del producto no puede estar vacío.", exception.getMessage());
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

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarTamano(productoDto.getTamano()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("Solo se permite XS,S,M,L, XL O XXL para el tamaño del producto.", exception.getMessage());
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
        productoDto.setTamano("XS");
        productoDto.setColor("A");//Color de 1 caracter,deberia ser invalido
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarColor(productoDto.getColor()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El color del producto debe tener entre 2 y 5 caracteres.", exception.getMessage());   }

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
        productoDto.setTamano("XS");
        productoDto.setColor("Ejemplo");//Color de mas de 5 caracteres, deberia ser invalido
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarColor(productoDto.getColor()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El color del producto debe tener entre 2 y 5 caracteres.", exception.getMessage());    }

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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul1");//Color con numeros
        productoDto.setPrecioUnitario(100.0);
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarColor(productoDto.getColor()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El color no debe contener números.", exception.getMessage());    }

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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(0.0);//Precio unitario vacio
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.valirdarPrecio(productoDto.getPrecioUnitario()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El precio unitario del producto no puede estar vacío.", exception.getMessage());    }

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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);//Precio unitario positivo
        productoDto.setStock(50);
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // No se debe lanzar ninguna excepción si todos los datos son válidos
        // Validamos cada uno de los campos del usuario
        productoService.validarNombre(productoDto.getNombre());
        productoService.validarDescripcion(productoDto.getDescripcion());
        productoService.validarTamano(productoDto.getTamano());
        productoService.validarColor(productoDto.getColor());
        productoService.valirdarPrecio(productoDto.getPrecioUnitario());
        productoService.validarStock(productoDto.getStock());
        productoService.validarUmbralBajoStock(productoDto.getUmbralBajoStock());}

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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(0);//Stock vacio
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarStock(productoDto.getStock()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El stock del producto no puede estar vacío.", exception.getMessage());    }

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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);//Stock positivo
        productoDto.setUmbralBajoStock(10);
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

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
        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaValida");

        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(0);//Umbral bajo stock vacio
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

        // Simulamos la validación
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarUmbralBajoStock(productoDto.getUmbralBajoStock()); // Llamamos al servicio para validar el nombre
        });

        assertEquals("El umbral bajo de stock del producto no puede estar vacío.", exception.getMessage());    }

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
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);//Umbral bajo stock positivo
        productoDto.setMarca(marcaDto);
        productoDto.setSubCategoria(subCategoriaDto);

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
        // Crear e insertar una marca en la base de datos en memoria
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNombre("MarcaValida");
        marcaRepository.save(marca);

        // Crear objetos SubCategoria
        SubCategoriaDTO subCategoriaDto = new SubCategoriaDTO();
        subCategoriaDto.setId(1L);
        subCategoriaDto.setNombre("SubCategoriaValida");

        // Configuración del ProductoDto con marca nula
        ProductoDto productoDto = new ProductoDto();
        productoDto.setNombre("NombreValido");
        productoDto.setDescripcion("Descripcion valida");
        productoDto.setTamano("XS");
        productoDto.setColor("Azul");
        productoDto.setPrecioUnitario(1.0);
        productoDto.setStock(1);
        productoDto.setUmbralBajoStock(1);
        productoDto.setMarca(null); // Marca vacía o nula
        productoDto.setSubCategoria(subCategoriaDto);

        // Convertir ProductoDto a Producto usando el mapper inyectado
        Producto producto = productoMapper.toEntity(productoDto);

        // Simulamos la validación de la marca a través del servicio
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarMarca(marca.getId(), producto); // Llamamos al servicio para validar la marca
        });

        // Verificamos que la excepción lanzada tenga el mensaje esperado
        assertEquals("La marca del producto no puede estar vacía.", exception.getMessage());
    }*/

    /*@Test
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

        // Simulamos la validación de la marca a través del servicio
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarMarca(marcaDto.getId(), producto); // Llamamos al servicio para validar la marca
        });

        // Verificamos que la excepción lanzada tenga el mensaje esperado
        assertEquals("La marca del producto no puede estar vacía.", exception.getMessage()); }*/

    /*@Test
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

        // Simulamos la validación de la marca a través del servicio
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarSubCategoria(subCategoriaDto.getId(), producto); // Llamamos al servicio para validar la marca
        });

        // Verificamos que la excepción lanzada tenga el mensaje esperado
        assertEquals("La marca del producto no puede estar vacía.", exception.getMessage());    }*/

    /*@Test
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


        assertTrue(productoDto.esValido(), "La subcategoria debería ser válida."); }*/
}