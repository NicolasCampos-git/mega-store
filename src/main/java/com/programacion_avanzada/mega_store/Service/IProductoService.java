package com.programacion_avanzada.mega_store.Service;

import java.util.List;

import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.Modelos.Producto;

public interface IProductoService {

    ProductoDto registrarProducto(ProductoDto dto);

    List<ProductoDto> listar();

    ProductoDto editarProducto(long id);

    void eliminar(Producto producto);
    
}
