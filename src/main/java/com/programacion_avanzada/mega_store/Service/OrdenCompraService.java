package com.programacion_avanzada.mega_store.Service;

import com.programacion_avanzada.mega_store.DTOs.ItemOrdenDto;
import com.programacion_avanzada.mega_store.DTOs.OrdenCompraDto;
import com.programacion_avanzada.mega_store.Mapper.OrdenCompraMapper;
import com.programacion_avanzada.mega_store.Modelos.*;
import com.programacion_avanzada.mega_store.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private EstadoOrdenRepository estadoOrdenRepository;

    @Override
    public OrdenCompraDto crearOrden(Long usuarioId, Map<Long, Integer> productosYCantidades) {
        //Se inicializa la orden
        OrdenCompra ordenCompra = new OrdenCompra();

        //Se le asigna usuario y estado
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        EstadoOrden estado = estadoOrdenRepository.findByNombre("PENDIENTE").orElseThrow(() -> new RuntimeException("Estado no encontrado"));
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
        return ordenCompraMapper.toDto(ordenCompra);
    }

    @Override
    public OrdenCompra cambiarEstado(Long ordenId, String nuevoEstado) {
        OrdenCompra orden = ordenCompraRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        EstadoOrden estado = estadoOrdenRepository.findByNombre(nuevoEstado)
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
}
