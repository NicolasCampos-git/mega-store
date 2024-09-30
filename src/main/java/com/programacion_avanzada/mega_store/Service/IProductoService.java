package com.programacion_avanzada.mega_store.Service;

import java.util.List;

import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;


public interface IProductoService {

    RegistrarProductoDto registrarProducto(RegistrarProductoDto dto);

    List<ProductoDto> listar();

    RegistrarProductoDto editarProducto(long id, RegistrarProductoDto dto);

    void eliminar(long id);
    
}
