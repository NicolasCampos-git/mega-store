package com.programacion_avanzada.mega_store.Service;

import java.util.List;

import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.Modelos.Producto;



public interface IProductoService {

    RegistrarProductoDto registrarProducto(RegistrarProductoDto dto);

    List<ProductoDto> listar();

    RegistrarProductoDto editarProducto(long id, RegistrarProductoDto dto);

    Producto buscarPorId(long id);

    Producto guardar(Producto producto);

    void eliminar(long id);

    void reactivar(long id);


    
}
