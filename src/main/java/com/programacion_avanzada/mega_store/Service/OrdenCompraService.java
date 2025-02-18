package com.programacion_avanzada.mega_store.Service;

import com.programacion_avanzada.mega_store.DTOs.OrdenDtos.OrdenCompraDto;
import com.programacion_avanzada.mega_store.Mapper.OrdenMappers.OrdenCompraMapper;
import com.programacion_avanzada.mega_store.Modelos.*;
import com.programacion_avanzada.mega_store.Repository.*;
import com.programacion_avanzada.mega_store.Service.Interfaces.IOrdenCompraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class OrdenCompraService implements IOrdenCompraService {
    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private OrdenCompraMapper ordenCompraMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ItemOrdenRepository itemOrdenRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Override
    public OrdenCompra crearOrden(Long usuarioId, Map<Long, Integer> productosYCantidades) {
        //Se inicializa la orden
        OrdenCompra ordenCompra = new OrdenCompra();

        //Se le asigna usuario y estado
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Estado estado = estadoRepository.findByNombre("PENDIENTE").orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        ordenCompra.setUsuario(usuario);
        ordenCompra.setEstado(estado);
        ordenCompra.setFecha(LocalDateTime.now());

        //Se crean los items y se agregan a la orden
        List<ItemOrden> items = new ArrayList<>();
        double total = 0.0;
        for (Map.Entry<Long, Integer> entry : productosYCantidades.entrySet()) {
            Producto producto = productoRepository.findById(entry.getKey()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            Integer cantidad = entry.getValue();

            //Se calcula el subtotal
            double subtotal = producto.getPrecioUnitario() * cantidad;
            total += subtotal;

            //Se crea el item de orden
            ItemOrden item = new ItemOrden();
            item.setProducto(producto);
            item.setCantidad(cantidad);
            item.setSubtotal(subtotal);

            items.add(item);
        }

        //Se guarda la orden y los items en la base de datos
        ordenCompra.setItems(items);
        ordenCompra.setTotal(total);
        ordenCompra = ordenCompraRepository.save(ordenCompra);  // Guardar orden primero para obtener ID

        //Se relacionan los items con la orden
        for (ItemOrden item : items) {
            item.setOrdenCompra(ordenCompra);
            itemOrdenRepository.save(item);
        }

        //Se mappea la orden a DTO usando el mapper
        return ordenCompra;
    }

    @Override
    public OrdenCompra cambiarEstado(Long ordenId, String nuevoEstado) {
        OrdenCompra orden = ordenCompraRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        Estado estado = estadoRepository.findByNombre(nuevoEstado)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        if (!esTransicionValida(orden.getEstado().getNombre(), nuevoEstado)) {
            throw new RuntimeException("Transición de estado no permitida");
        }

        orden.setEstado(estado);
        return ordenCompraRepository.save(orden);
    }

    private boolean esTransicionValida(String estadoActual, String nuevoEstado) {
        Map<String, List<String>> transiciones = Map.of(
                "Pendiente", List.of("Confirmada", "Cancelada"),
                "Confirmada", List.of("Enviada", "Cancelada"),
                "Enviada", List.of("Entregada")
        );

        return transiciones.getOrDefault(estadoActual, List.of()).contains(nuevoEstado);
    }

    public OrdenCompra obtenerOrdenPorId(Long id) {
        return ordenCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));
    }

    //Metodo para el listado de todas las ordenes de compra.
    @Override
    public List<OrdenCompra> obtenerOrdenes() {
        List<OrdenCompra> ordenes =  ordenCompraRepository.findAll();
        if(ordenes.isEmpty()){
            throw new RuntimeException("No hay ordenes de compra");
        }
        return ordenes;
    }

    //Metodo para el listado de todas las ordenes de compra por usuario.
    public List<OrdenCompra> obtenerOrdenesPorUsuario(Long usuarioId) {
        validarUsuario(usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ordenCompraRepository.findByUsuario(usuario);
    }

    @Override
    public void eliminarOrden(Long id) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));

        orden.setEstaActiva(false);
        
        ordenCompraRepository.save(orden);
    }

    @Override
    public void reactivarOrden(Long id) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));

        orden.setEstaActiva(true);

        ordenCompraRepository.save(orden);
    }

    @Override
    public OrdenCompra cancelarOrden(Long id){
        validarIdOrdenCompra(id);
        OrdenCompra orden = ordenCompraRepository.findById(id).get();
        
        if(orden.getEstado().getNombre().equals("Entregada")){
            throw new RuntimeException("No se puede cancelar una orden entregada");
        }
        orden.setEstado(estadoRepository.findByNombre("Cancelada").get());   
        orden.setFechaCancelacion(LocalDateTime.now());

        return ordenCompraRepository.save(orden);
    }

    

    public void validarIdOrdenCompra(Long id) {
        if (!ordenCompraRepository.existsById(id)) {
            throw new RuntimeException("Orden de compra no encontrada");
        }
        
    }

    public boolean existeOrdenCompra(Long id) {
        return ordenCompraRepository.existsById(id);
    }


    private void validarUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RuntimeException("Usuario no encontrado");
        }

        
    }

    

   
    
    
}
