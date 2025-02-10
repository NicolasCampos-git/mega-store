package com.programacion_avanzada.mega_store.Service;

import com.programacion_avanzada.mega_store.DTOs.OrdenCompraDto;
import com.programacion_avanzada.mega_store.Modelos.OrdenCompra;

import java.util.List;
import java.util.Map;

public interface IOrdenCompraService {
    
    OrdenCompra cambiarEstado(Long ordenId, String nuevoEstado);

    OrdenCompraDto crearOrden(Long usuarioId, Map<Long, Integer> productosYCantidades);

    OrdenCompra obtenerOrdenPorId(Long id);

    List<OrdenCompra> obtenerOrdenes();

    List<OrdenCompra> obtenerOrdenesPorUsuario(Long usuarioId);
}
