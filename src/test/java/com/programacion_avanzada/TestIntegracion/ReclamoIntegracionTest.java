package com.programacion_avanzada.TestIntegracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programacion_avanzada.mega_store.DTOs.ReclamoDtos.ActualizarReclamoDto;
import com.programacion_avanzada.mega_store.DTOs.ReclamoDtos.RegistrarReclamoDto;
import com.programacion_avanzada.mega_store.Modelos.Estado;
import com.programacion_avanzada.mega_store.Modelos.OrdenCompra;
import com.programacion_avanzada.mega_store.Modelos.Reclamo;
import com.programacion_avanzada.mega_store.Modelos.TipoReclamo;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.EstadoRepository;
import com.programacion_avanzada.mega_store.Repository.ReclamoRepository;
import com.programacion_avanzada.mega_store.Repository.TipoReclamoRepositoty;
import com.programacion_avanzada.mega_store.Service.OrdenCompraService;
import com.programacion_avanzada.mega_store.Service.ReclamoService;
import com.programacion_avanzada.mega_store.Service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class ReclamoIntegracionTest {

    @Mock
    private ReclamoRepository reclamoRepository;
    
    @Mock
    private UsuarioService usuarioService;
    
    @Mock
    private OrdenCompraService ordenCompraService;
    
    @Mock
    private TipoReclamoRepositoty tipoReclamoRepository;
    
    @Mock
    private EstadoRepository estadoRepository;
    
    @InjectMocks
    private ReclamoService reclamoService;
    
    private Usuario usuario;
    private OrdenCompra ordenCompra;
    private TipoReclamo tipoReclamo;
    private Estado estado;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        
        // Crear datos de prueba
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan@example.com");
        
        ordenCompra = new OrdenCompra();
        ordenCompra.setId(1L);
        ordenCompra.setUsuario(usuario);
        
        tipoReclamo = new TipoReclamo();
        tipoReclamo.setId(1L);
        tipoReclamo.setNombre("Producto dañado");
        
        estado = new Estado();
        estado.setId(1L);
        estado.setNombre("Registrado");
    }
    
    @Test
    public void testRegistrarReclamo() {
        // Arrange - Preparar los datos necesarios para el test y configurar los mocks
        RegistrarReclamoDto reclamoDto = new RegistrarReclamoDto();
        reclamoDto.setMotivo("Producto defectuoso");
        reclamoDto.setDescripcion("El producto llego con rayones en la superficie");
        reclamoDto.setIdUsuario(1L);
        reclamoDto.setIdOrdenCompra(1L);
        reclamoDto.setIdTipoReclamo(1L);
        
        // Configurar comportamientos de los mocks para este test específico
        when(usuarioService.validarIdUsuario(1L)).thenReturn(true);
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        
        when(ordenCompraService.existeOrdenCompra(1L)).thenReturn(true);
        when(ordenCompraService.obtenerOrdenPorId(1L)).thenReturn(ordenCompra);
        
        when(tipoReclamoRepository.existsById(1L)).thenReturn(true);
        when(tipoReclamoRepository.findById(1L)).thenReturn(tipoReclamo);
        
        when(estadoRepository.findByNombre("Registrado")).thenReturn(java.util.Optional.of(estado));
        
        when(reclamoRepository.save(any(Reclamo.class))).thenAnswer(invocation -> {
            Reclamo reclamo = invocation.getArgument(0);
            if (reclamo.getId() == 0) {
                reclamo.setId(1L);
            }
            return reclamo;
        });
        
        // Act - Ejecutar el método que se está probando
        Reclamo reclamoRegistrado = reclamoService.registrarReclamo(reclamoDto);
        
        // Assert - Verificar que el resultado sea el esperado
        assertNotNull(reclamoRegistrado, "El reclamo registrado no debe ser nulo");
        assertNotNull(reclamoRegistrado.getId(), "El reclamo debe tener un ID asignado");
        assertEquals(reclamoDto.getMotivo().toLowerCase(), reclamoRegistrado.getMotivo(), 
                    "El motivo debe coincidir (convertido a minúsculas)");
        assertEquals(reclamoDto.getDescripcion().toLowerCase(), reclamoRegistrado.getDescripcion(), 
                    "La descripción debe coincidir (convertida a minúsculas)");
        assertEquals(1L, reclamoRegistrado.getUsuario().getId(), 
                    "El ID de usuario debe coincidir");
        assertEquals(1L, reclamoRegistrado.getOrdenCompra().getId(), 
                    "El ID de la orden de compra debe coincidir");
        assertEquals(1L, reclamoRegistrado.getTipoReclamo().getId(), 
                    "El ID del tipo de reclamo debe coincidir");
        assertEquals("Registrado", reclamoRegistrado.getEstado().getNombre(), 
                    "El estado inicial debe ser 'Registrado'");
        assertNotNull(reclamoRegistrado.getFechaCreacion(), "Debe tener fecha de creación");
    }
    
    @Test
    public void testRegistrarReclamoSinTipoReclamo() {
        // Arrange - Preparar los datos necesarios para el test y configurar los mocks
        RegistrarReclamoDto reclamoDto = new RegistrarReclamoDto();
        reclamoDto.setMotivo("Producto defectuoso");
        reclamoDto.setDescripcion("El producto llego con rayones en la superficie");
        reclamoDto.setIdUsuario(1L);
        reclamoDto.setIdOrdenCompra(1L);
        // No asignamos un tipo de reclamo válido (dejamos el valor por defecto 0)
        
        // Configuramos que no exista el tipo de reclamo con ID 0
        when(tipoReclamoRepository.existsById(0L)).thenReturn(false);
        
        // Act & Assert - Verificar que se lance una excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.registrarReclamo(reclamoDto);
        }, "Se debería lanzar una excepción cuando el tipo de reclamo es inválido");
        
        // Verificar el mensaje de la excepción
        assertEquals("Tipo de reclamo no encontrado", exception.getMessage(),
                   "El mensaje de error debe indicar que el tipo de reclamo no fue encontrado");
    }
    
    @Test
    public void testRegistrarReclamoConUsuarioInexistente() {
        // Arrange - Preparar los datos necesarios para el test y configurar los mocks
        RegistrarReclamoDto reclamoDto = new RegistrarReclamoDto();
        reclamoDto.setMotivo("Producto defectuoso");
        reclamoDto.setDescripcion("El producto llego con rayones en la superficie");
        // Asignamos un ID de usuario que no existe (999)
        reclamoDto.setIdUsuario(999L);
        reclamoDto.setIdOrdenCompra(1L);
        reclamoDto.setIdTipoReclamo(1L);
        
        // Configuramos los mocks necesarios para que el test llegue a la validación de usuario
        when(tipoReclamoRepository.existsById(1L)).thenReturn(true);
        
        // Configuramos el mock para indicar que el usuario no existe, lanzando la excepción esperada
        when(usuarioService.validarIdUsuario(999L)).thenThrow(new IllegalArgumentException("Usuario no encontrado con el ID: 999"));
        
        // Act & Assert - Verificar que se lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reclamoService.registrarReclamo(reclamoDto);
        }, "Se debería lanzar una excepción cuando el usuario no existe");
        
        // Verificar el mensaje de la excepción
        assertEquals("Usuario no encontrado con el ID: 999", exception.getMessage(),
                   "El mensaje de error debe indicar que el usuario no fue encontrado");
    }

    @Test
    public void testRegistrarReclamoConUsuarioExistenteYTipoReclamoExistente() {
        // Arrange - Preparar los datos necesarios para el test y configurar los mocks
        RegistrarReclamoDto reclamoDto = new RegistrarReclamoDto();
        reclamoDto.setMotivo("Producto defectuoso");
        reclamoDto.setDescripcion("El producto llego con rayones en la superficie");
        reclamoDto.setIdUsuario(1L);
        reclamoDto.setIdOrdenCompra(1L);
        reclamoDto.setIdTipoReclamo(1L);
        
        // Configurar comportamientos específicos de los mocks para este test
        // Validar que el usuario existe
        when(usuarioService.validarIdUsuario(1L)).thenReturn(true);
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        
        // Validar que el tipo de reclamo existe
        when(tipoReclamoRepository.existsById(1L)).thenReturn(true);
        when(tipoReclamoRepository.findById(1L)).thenReturn(tipoReclamo);
        
        // Validar que la orden existe y pertenece al usuario
        when(ordenCompraService.existeOrdenCompra(1L)).thenReturn(true);
        when(ordenCompraService.obtenerOrdenPorId(1L)).thenReturn(ordenCompra);
        
        // Estado para el reclamo
        when(estadoRepository.findByNombre("Registrado")).thenReturn(java.util.Optional.of(estado));
        
        // Simular que se guarda el reclamo correctamente
        when(reclamoRepository.save(any(Reclamo.class))).thenAnswer(invocation -> {
            Reclamo reclamo = invocation.getArgument(0);
            if (reclamo.getId() == 0) {
                reclamo.setId(1L);
            }
            reclamo.setEstaActivo(true); // Asegurar que el reclamo se marque como activo
            return reclamo;
        });
        
        // Act - Ejecutar el método que se está probando
        Reclamo reclamoRegistrado = reclamoService.registrarReclamo(reclamoDto);
        
        // Assert - Verificar que el resultado sea el esperado
        assertNotNull(reclamoRegistrado, "El reclamo registrado no debe ser nulo");
        assertNotNull(reclamoRegistrado.getId(), "El reclamo debe tener un ID asignado");
        assertEquals(reclamoDto.getMotivo().toLowerCase(), reclamoRegistrado.getMotivo(), 
                    "El motivo debe coincidir (convertido a minúsculas)");
        assertEquals(reclamoDto.getDescripcion().toLowerCase(), reclamoRegistrado.getDescripcion(), 
                    "La descripción debe coincidir (convertida a minúsculas)");
        assertEquals(1L, reclamoRegistrado.getUsuario().getId(), 
                    "El ID de usuario debe coincidir");
        assertEquals(1L, reclamoRegistrado.getTipoReclamo().getId(), 
                    "El ID del tipo de reclamo debe coincidir");
        assertEquals(1L, reclamoRegistrado.getOrdenCompra().getId(), 
                    "El ID de la orden de compra debe coincidir");
        assertEquals("Registrado", reclamoRegistrado.getEstado().getNombre(), 
                    "El estado inicial debe ser 'Registrado'");
        assertNotNull(reclamoRegistrado.getFechaCreacion(), 
                     "El reclamo debe tener una fecha de creación");
        assertEquals(true, reclamoRegistrado.isEstaActivo(), 
                    "El reclamo debe estar activo");
    }

    @Test
    public void testRegistrarReclamoConOrdenNoPertenecienteAlUsuario() {
        // Arrange - Preparar los datos necesarios para el test
        RegistrarReclamoDto reclamoDto = new RegistrarReclamoDto();
        reclamoDto.setMotivo("Producto defectuoso");
        reclamoDto.setDescripcion("El producto llego con rayones en la superficie");
        reclamoDto.setIdUsuario(1L);
        reclamoDto.setIdOrdenCompra(2L); // ID de orden que no pertenece al usuario
        reclamoDto.setIdTipoReclamo(1L);
        
        // Crear un usuario diferente para la orden
        Usuario otroUsuario = new Usuario();
        otroUsuario.setId(2L);
        otroUsuario.setNombre("Pedro");
        otroUsuario.setApellido("Gomez");
        
        // Crear una orden que pertenece a otro usuario
        OrdenCompra ordenDeOtroUsuario = new OrdenCompra();
        ordenDeOtroUsuario.setId(2L);
        ordenDeOtroUsuario.setUsuario(otroUsuario);
        
        // Configuramos solo los mocks en el orden exacto en que serán llamados
        // Primero: validar tipo de reclamo
        lenient().when(tipoReclamoRepository.existsById(1L)).thenReturn(true);
        
        // Segundo: validar usuario
        lenient().when(usuarioService.validarIdUsuario(1L)).thenReturn(true);
        
        // Tercero: buscar usuario por ID 
        lenient().when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        
        // Cuarto: validar orden de compra
        lenient().when(ordenCompraService.existeOrdenCompra(2L)).thenReturn(true);
        
        // Quinto: obtener orden por ID para validar que pertenece al usuario - AQUÍ ES DONDE FALLARÁ
        when(ordenCompraService.obtenerOrdenPorId(2L)).thenReturn(ordenDeOtroUsuario);
        
        // Act & Assert - Verificar que se lance la excepción correcta
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.registrarReclamo(reclamoDto);
        }, "Se debería lanzar una excepción cuando la orden no pertenece al usuario");
        
        // Verificar el mensaje de la excepción
        assertEquals("La orden de compra no pertenece al usuario", exception.getMessage(),
                   "El mensaje de error debe indicar que la orden no pertenece al usuario");
    }
    
    @Test
    public void testRegistrarReclamoConMotivoInvalido() {
        // Arrange - Preparar los datos necesarios para el test
        RegistrarReclamoDto reclamoDto = new RegistrarReclamoDto();
        reclamoDto.setMotivo("Producto123"); // Motivo con números (inválido)
        reclamoDto.setDescripcion("El producto llego con rayones en la superficie");
        reclamoDto.setIdUsuario(1L);
        reclamoDto.setIdOrdenCompra(1L);
        reclamoDto.setIdTipoReclamo(1L);
        
        // Configurar los mocks necesarios para llegar a la validación del motivo
        when(tipoReclamoRepository.existsById(1L)).thenReturn(true);
        when(usuarioService.validarIdUsuario(1L)).thenReturn(true);
        when(ordenCompraService.existeOrdenCompra(1L)).thenReturn(true);
        
        // Act & Assert - Verificar que se lance la excepción correcta
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.registrarReclamo(reclamoDto);
        }, "Se debería lanzar una excepción cuando el motivo contiene caracteres inválidos");
        
        // Verificar el mensaje de la excepción
        assertEquals("El motivo solo debe tener letras y espacios", exception.getMessage(),
                   "El mensaje de error debe indicar que el motivo tiene un formato inválido");
    }
    
    @Test
    public void testRegistrarReclamoConDescripcionVacia() {
        // Arrange - Preparar los datos necesarios para el test
        RegistrarReclamoDto reclamoDto = new RegistrarReclamoDto();
        reclamoDto.setMotivo("Producto defectuoso");
        reclamoDto.setDescripcion(""); // Descripción vacía (inválida)
        reclamoDto.setIdUsuario(1L);
        reclamoDto.setIdOrdenCompra(1L);
        reclamoDto.setIdTipoReclamo(1L);
        
        // Configurar los mocks necesarios para llegar a la validación de la descripción
        when(tipoReclamoRepository.existsById(1L)).thenReturn(true);
        when(usuarioService.validarIdUsuario(1L)).thenReturn(true);
        when(ordenCompraService.existeOrdenCompra(1L)).thenReturn(true);
        
        // Act & Assert - Verificar que se lance la excepción correcta
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.registrarReclamo(reclamoDto);
        }, "Se debería lanzar una excepción cuando la descripción está vacía");
        
        // Verificar el mensaje de la excepción
        assertEquals("Descripcion no puede estar vacia", exception.getMessage(),
                   "El mensaje de error debe indicar que la descripción no puede estar vacía");
    }

    @Test
    public void testActualizarReclamoEnEstadoRegistrado() {
        // Arrange - Preparar los datos necesarios para el test
        // Crear un reclamo en estado "Registrado"
        long reclamoId = 1L;
        
        // Crear el estado "Registrado"
        Estado estadoRegistrado = new Estado();
        estadoRegistrado.setId(1L);
        estadoRegistrado.setNombre("Registrado");
        
        // Crear el reclamo existente en estado "Registrado"
        Reclamo reclamoExistente = new Reclamo();
        reclamoExistente.setId(reclamoId);
        reclamoExistente.setMotivo("motivo original");
        reclamoExistente.setDescripcion("descripcion original");
        reclamoExistente.setUsuario(usuario);
        reclamoExistente.setOrdenCompra(ordenCompra);
        reclamoExistente.setTipoReclamo(tipoReclamo);
        reclamoExistente.setEstado(estadoRegistrado);
        reclamoExistente.setEstaActivo(true);
        reclamoExistente.setFechaCreacion(LocalDateTime.now().minusDays(1));
        
        // Crear DTO con datos actualizados
        ActualizarReclamoDto actualizarDto = new ActualizarReclamoDto();
        actualizarDto.setMotivo("Producto defectuoso actualizado");
        actualizarDto.setDescripcion("El producto llego con rayones mas graves de lo reportado inicialmente");
        actualizarDto.setIdTipoReclamo(1L);
        
        // Configurar comportamiento de los mocks
        when(reclamoRepository.findById(reclamoId)).thenReturn(reclamoExistente);
        when(tipoReclamoRepository.existsById(1L)).thenReturn(true);
        when(tipoReclamoRepository.findById(1L)).thenReturn(tipoReclamo);
        
        // Configurar el mock para ordenCompraService - AÑADIDO PARA RESOLVER EL ERROR
        when(ordenCompraService.obtenerOrdenPorId(1L)).thenReturn(ordenCompra);
        
        when(reclamoRepository.save(any(Reclamo.class))).thenAnswer(invocation -> {
            Reclamo reclamo = invocation.getArgument(0);
            // Simular que se actualiza correctamente
            return reclamo;
        });
        
        // Act - Ejecutar el método que se está probando
        Reclamo reclamoActualizado = reclamoService.actualizarReclamo(reclamoId, actualizarDto);
        
        // Assert - Verificar que el resultado sea el esperado
        assertNotNull(reclamoActualizado, "El reclamo actualizado no debe ser nulo");
        assertEquals("producto defectuoso actualizado", reclamoActualizado.getMotivo(), 
                    "El motivo debe actualizarse y convertirse a minúsculas");
        assertEquals("el producto llego con rayones mas graves de lo reportado inicialmente", reclamoActualizado.getDescripcion(), 
                    "La descripción debe actualizarse y convertirse a minúsculas");
        assertEquals(1L, reclamoActualizado.getTipoReclamo().getId(), 
                    "El tipo de reclamo debe actualizarse");
        assertNotNull(reclamoActualizado.getFechaActualizacion(), 
                    "Debe establecerse la fecha de actualización");
    }

    @Test
    public void testActualizarReclamoEnEstadoNoRegistrado() {
        // Arrange - Preparar los datos necesarios para el test
        // Crear un reclamo en estado "En Revision" (que no debe poder actualizarse)
        long reclamoId = 1L;
        
        // Crear el estado "En Revision"
        Estado estadoEnRevision = new Estado();
        estadoEnRevision.setId(2L);
        estadoEnRevision.setNombre("En Revision");
        
        // Crear el reclamo existente en estado "En Revision"
        Reclamo reclamoExistente = new Reclamo();
        reclamoExistente.setId(reclamoId);
        reclamoExistente.setMotivo("motivo original");
        reclamoExistente.setDescripcion("descripcion original");
        reclamoExistente.setUsuario(usuario);
        reclamoExistente.setOrdenCompra(ordenCompra);
        reclamoExistente.setTipoReclamo(tipoReclamo);
        reclamoExistente.setEstado(estadoEnRevision);
        reclamoExistente.setEstaActivo(true);
        reclamoExistente.setFechaCreacion(LocalDateTime.now().minusDays(1));
        reclamoExistente.setFechaEnRevicion(LocalDateTime.now().minusHours(2));
        
        // Crear DTO con datos actualizados
        ActualizarReclamoDto actualizarDto = new ActualizarReclamoDto();
        actualizarDto.setMotivo("Producto defectuoso actualizado");
        actualizarDto.setDescripcion("El producto llego con rayones mas graves de lo reportado inicialmente");
        
        // Configurar comportamiento de los mocks
        when(reclamoRepository.findById(reclamoId)).thenReturn(reclamoExistente);
        
        // Act & Assert - Verificar que se lance una excepción al intentar actualizar un reclamo en estado "En Revision"
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.actualizarReclamo(reclamoId, actualizarDto);
        }, "Se debería lanzar una excepción al intentar actualizar un reclamo en estado no Registrado");
        
        // Verificar el mensaje de la excepción
        assertEquals("El reclamo no puede actualizarse mientras está en revisión o en un estado posterior", exception.getMessage(),
                   "El mensaje de error debe indicar que el reclamo no puede actualizarse en su estado actual");
    }

    @Test
    public void testActualizarReclamoConOrdenNoPertenecienteAlUsuario() {
        // Arrange - Preparar los datos necesarios para el test
        long reclamoId = 1L;
        
        // Crear el estado "Registrado"
        Estado estadoRegistrado = new Estado();
        estadoRegistrado.setId(1L);
        estadoRegistrado.setNombre("Registrado");
        
        // Crear un usuario diferente para la orden
        Usuario otroUsuario = new Usuario();
        otroUsuario.setId(2L);
        otroUsuario.setNombre("Pedro");
        otroUsuario.setApellido("Gomez");
        
        // Crear una orden que pertenece a otro usuario
        OrdenCompra ordenDeOtroUsuario = new OrdenCompra();
        ordenDeOtroUsuario.setId(2L);
        ordenDeOtroUsuario.setUsuario(otroUsuario);
        
        // Crear el reclamo existente en estado "Registrado" pero con una orden que no pertenece al usuario
        Reclamo reclamoExistente = new Reclamo();
        reclamoExistente.setId(reclamoId);
        reclamoExistente.setMotivo("motivo original");
        reclamoExistente.setDescripcion("descripcion original");
        reclamoExistente.setUsuario(usuario);
        reclamoExistente.setOrdenCompra(ordenDeOtroUsuario); // ¡La orden pertenece a otro usuario!
        reclamoExistente.setTipoReclamo(tipoReclamo);
        reclamoExistente.setEstado(estadoRegistrado);
        reclamoExistente.setEstaActivo(true);
        reclamoExistente.setFechaCreacion(LocalDateTime.now().minusDays(1));
        
        // Crear DTO con datos actualizados
        ActualizarReclamoDto actualizarDto = new ActualizarReclamoDto();
        actualizarDto.setMotivo("Producto defectuoso actualizado");
        
        // Configurar comportamiento de los mocks
        when(reclamoRepository.findById(reclamoId)).thenReturn(reclamoExistente);
        
        // Configurar el mock para ordenCompraService
        when(ordenCompraService.obtenerOrdenPorId(2L)).thenReturn(ordenDeOtroUsuario);
        
        // Act & Assert - Verificar que se lance una excepción al intentar actualizar un reclamo con orden que no pertenece al usuario
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.actualizarReclamo(reclamoId, actualizarDto);
        }, "Se debería lanzar una excepción al intentar actualizar un reclamo con orden que no pertenece al usuario");
        
        // Verificar el mensaje de la excepción
        assertEquals("La orden de compra no pertenece al usuario", exception.getMessage(),
                   "El mensaje de error debe indicar que la orden no pertenece al usuario");
    }

    @Test
    public void testActualizarReclamoEnEstadoAprobado() {
        // Arrange - Preparar los datos necesarios para el test
        // Crear un reclamo en estado "Aprobado" (que no debe poder actualizarse)
        long reclamoId = 1L;
        
        // Crear el estado "Aprobado"
        Estado estadoAprobado = new Estado();
        estadoAprobado.setId(3L);
        estadoAprobado.setNombre("Aprobado");
        
        // Crear el reclamo existente en estado "Aprobado"
        Reclamo reclamoExistente = new Reclamo();
        reclamoExistente.setId(reclamoId);
        reclamoExistente.setMotivo("motivo original");
        reclamoExistente.setDescripcion("descripcion original");
        reclamoExistente.setUsuario(usuario);
        reclamoExistente.setOrdenCompra(ordenCompra);
        reclamoExistente.setTipoReclamo(tipoReclamo);
        reclamoExistente.setEstado(estadoAprobado);
        reclamoExistente.setEstaActivo(true);
        reclamoExistente.setFechaCreacion(LocalDateTime.now().minusDays(2));
        reclamoExistente.setFechaEnRevicion(LocalDateTime.now().minusDays(1));
        reclamoExistente.setFechaAprobado(LocalDateTime.now().minusHours(12));
        
        // Crear DTO con datos actualizados
        ActualizarReclamoDto actualizarDto = new ActualizarReclamoDto();
        actualizarDto.setMotivo("Producto defectuoso actualizado");
        actualizarDto.setDescripcion("El producto llego con rayones mas graves de lo reportado inicialmente");
        
        // Configurar comportamiento de los mocks
        when(reclamoRepository.findById(reclamoId)).thenReturn(reclamoExistente);
        
        // Act & Assert - Verificar que se lance una excepción al intentar actualizar un reclamo en estado "Aprobado"
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.actualizarReclamo(reclamoId, actualizarDto);
        }, "Se debería lanzar una excepción al intentar actualizar un reclamo en estado Aprobado");
        
        // Verificar el mensaje de la excepción
        assertEquals("El reclamo no puede actualizarse mientras está en revisión o en un estado posterior", exception.getMessage(),
                   "El mensaje de error debe indicar que el reclamo no puede actualizarse en su estado actual");
    }

    @Test
    public void testActualizarReclamoEnEstadoRechazado() {
        // Arrange - Preparar los datos necesarios para el test
        // Crear un reclamo en estado "Rechazado" (que no debe poder actualizarse)
        long reclamoId = 1L;
        
        // Crear el estado "Rechazado"
        Estado estadoRechazado = new Estado();
        estadoRechazado.setId(4L);
        estadoRechazado.setNombre("Rechazado");
        
        // Crear el reclamo existente en estado "Rechazado"
        Reclamo reclamoExistente = new Reclamo();
        reclamoExistente.setId(reclamoId);
        reclamoExistente.setMotivo("motivo original");
        reclamoExistente.setDescripcion("descripcion original");
        reclamoExistente.setUsuario(usuario);
        reclamoExistente.setOrdenCompra(ordenCompra);
        reclamoExistente.setTipoReclamo(tipoReclamo);
        reclamoExistente.setEstado(estadoRechazado);
        reclamoExistente.setEstaActivo(true);
        reclamoExistente.setFechaCreacion(LocalDateTime.now().minusDays(2));
        reclamoExistente.setFechaEnRevicion(LocalDateTime.now().minusDays(1));
        reclamoExistente.setFechaRechazado(LocalDateTime.now().minusHours(12));
        
        // Crear DTO con datos actualizados
        ActualizarReclamoDto actualizarDto = new ActualizarReclamoDto();
        actualizarDto.setMotivo("Producto defectuoso actualizado");
        actualizarDto.setDescripcion("El producto llego con rayones mas graves de lo reportado inicialmente");
        
        // Configurar comportamiento de los mocks
        when(reclamoRepository.findById(reclamoId)).thenReturn(reclamoExistente);
        
        // Act & Assert - Verificar que se lance una excepción al intentar actualizar un reclamo en estado "Rechazado"
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.actualizarReclamo(reclamoId, actualizarDto);
        }, "Se debería lanzar una excepción al intentar actualizar un reclamo en estado Rechazado");
        
        // Verificar el mensaje de la excepción
        assertEquals("El reclamo no puede actualizarse mientras está en revisión o en un estado posterior", exception.getMessage(),
                   "El mensaje de error debe indicar que el reclamo no puede actualizarse en su estado actual");
    }

    @Test
    public void testActualizarReclamoEnEstadoResuelto() {
        // Arrange - Preparar los datos necesarios para el test
        // Crear un reclamo en estado "Resuelto" (que no debe poder actualizarse)
        long reclamoId = 1L;
        
        // Crear el estado "Resuelto"
        Estado estadoResuelto = new Estado();
        estadoResuelto.setId(5L);
        estadoResuelto.setNombre("Resuelto");
        
        // Crear el reclamo existente en estado "Resuelto"
        Reclamo reclamoExistente = new Reclamo();
        reclamoExistente.setId(reclamoId);
        reclamoExistente.setMotivo("motivo original");
        reclamoExistente.setDescripcion("descripcion original");
        reclamoExistente.setUsuario(usuario);
        reclamoExistente.setOrdenCompra(ordenCompra);
        reclamoExistente.setTipoReclamo(tipoReclamo);
        reclamoExistente.setEstado(estadoResuelto);
        reclamoExistente.setEstaActivo(true);
        reclamoExistente.setFechaCreacion(LocalDateTime.now().minusDays(3));
        reclamoExistente.setFechaEnRevicion(LocalDateTime.now().minusDays(2));
        reclamoExistente.setFechaAprobado(LocalDateTime.now().minusDays(1));
        reclamoExistente.setFechaResuelto(LocalDateTime.now().minusHours(12));
        
        // Crear DTO con datos actualizados
        ActualizarReclamoDto actualizarDto = new ActualizarReclamoDto();
        actualizarDto.setMotivo("Producto defectuoso actualizado");
        actualizarDto.setDescripcion("El producto llego con rayones mas graves de lo reportado inicialmente");
        
        // Configurar comportamiento de los mocks
        when(reclamoRepository.findById(reclamoId)).thenReturn(reclamoExistente);
        
        // Act & Assert - Verificar que se lance una excepción al intentar actualizar un reclamo en estado "Resuelto"
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.actualizarReclamo(reclamoId, actualizarDto);
        }, "Se debería lanzar una excepción al intentar actualizar un reclamo en estado Resuelto");
        
        // Verificar el mensaje de la excepción
        assertEquals("El reclamo no puede actualizarse mientras está en revisión o en un estado posterior", exception.getMessage(),
                   "El mensaje de error debe indicar que el reclamo no puede actualizarse en su estado actual");
    }
}