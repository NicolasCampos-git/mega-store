package com.programacion_avanzada.mega_store.service;

import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;
import com.programacion_avanzada.mega_store.Mapper.RegistrarProductoMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;
import com.programacion_avanzada.mega_store.Repository.ProductoRepository;
import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;
import com.programacion_avanzada.mega_store.Service.ProductoService;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProductoServiceTest {

    @InjectMocks
    private ProductoService productoService;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private SubCategoriaRepository subCategoriaRepository;

    @Mock
    private RegistrarProductoMapper registrarProductoMapper;

    private ProductoDto productoDto;

    @BeforeEach
    public void setUp() {
        productoDto = new ProductoDto();
        // No es necesario crear manualmente la instancia de categoriaService,
        // ya que Mockito se encarga de ello con @InjectMocks
    }

    @Test
    void testRegistrarProducto() {
        // Crear un objeto DTO de prueba
        RegistrarProductoDto dto = new RegistrarProductoDto();
        dto.setNombre("Producto");
        dto.setDescripcion("Descripción válida");
        dto.setColor("Rojo");
        dto.setTamano("Mediano");
        dto.setStock(100);
        dto.setPrecioUnitario(29.99);
        dto.setMarcaId(1L); // Asegúrate de que coincide con el mock
        dto.setSubCategoriaId(1L); // Asegúrate de que coincide con el mock

        // Crear objetos Marca y SubCategoria para asociarlos al Producto
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNombre("MarcaValida");

        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setId(1L);
        subCategoria.setNombre("SubCategoriaValida");

        // Configurar el comportamiento del mock para la marca
        when(marcaRepository.existsById(1L)).thenReturn(true);
        when(marcaRepository.findById(1L)).thenReturn(Optional.of(marca));

        // Configurar el comportamiento del mock para la subcategoría
        when(subCategoriaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.findById(1L)).thenReturn(Optional.of(subCategoria));

        // Verificar si el nombre del producto ya existe
        when(productoRepository.existsByNombre("Producto")).thenReturn(false); // Debe devolver false para que pase la verificación

        // Configurar el comportamiento del mock para convertir el DTO a Producto
        Producto productoEsperado = new Producto();
        productoEsperado.setNombre("Producto");
        productoEsperado.setDescripcion("Descripción válida");
        productoEsperado.setColor("Rojo");
        productoEsperado.setTamano("Mediano");
        productoEsperado.setStock(100);
        productoEsperado.setPrecioUnitario(29.99);
        productoEsperado.setMarca(marca);
        productoEsperado.setSubcategoria(subCategoria);

        when(registrarProductoMapper.toEntity(dto)).thenReturn(productoEsperado);

        // Simular el comportamiento del repositorio para guardar el producto
        when(productoRepository.save(any(Producto.class))).thenReturn(productoEsperado);
        when(registrarProductoMapper.toDto(productoEsperado)).thenReturn(dto);

        // Ejecutar el metodo a probar
        RegistrarProductoDto result = productoService.registrarProducto(dto);

        // Verificar los resultados
        assertNotNull(result, "El resultado no debería ser nulo");
        assertEquals("Producto", result.getNombre());
        assertEquals("Descripción válida", result.getDescripcion());

        // Verificar que se hayan llamado los métodos esperados
        verify(marcaRepository).existsById(1L);
        verify(marcaRepository).findById(1L);
        verify(subCategoriaRepository).existsById(1L);
        verify(subCategoriaRepository).findById(1L);
        verify(productoRepository).existsByNombre("Producto");
        verify(productoRepository).save(productoEsperado);
    }







    @Test
    void testRegistrarProductoYaExistente() {
        // Crear un objeto DTO de prueba
        RegistrarProductoDto dto = new RegistrarProductoDto();
        dto.setNombre("ProductoExistente");
        dto.setDescripcion("Descripción existente");
        dto.setColor("Rojo");
        dto.setTamano("Mediano");
        dto.setStock(100);
        dto.setPrecioUnitario(29.99);
        dto.setMarcaId(1L); // Asegúrate de que coincide con el mock
        dto.setSubCategoriaId(1L); // Asegúrate de que coincide con el mock

        // Simular que la categoría existe
        when(marcaRepository.existsById(1L)).thenReturn(true);
        when(subCategoriaRepository.existsById(1L)).thenReturn(true);
        // Simular que la subcategoría ya existe en el repositorio
        when(productoRepository.existsByNombre("ProductoExistente")).thenReturn(true);

        // Ejecutar el método a probar y verificar que se lanza la excepción
        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
            productoService.registrarProducto(dto);
        });

        assertEquals("El producto ya existe", exception.getMessage());
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

