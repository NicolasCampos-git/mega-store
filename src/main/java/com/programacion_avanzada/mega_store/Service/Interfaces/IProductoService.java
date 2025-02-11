package com.programacion_avanzada.mega_store.Service.Interfaces;

import java.util.List;


import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.Modelos.Producto;



public interface IProductoService {

    Producto registrarProducto(RegistrarProductoDto dto);

    List<Producto> listar();

    Producto editarProducto(long id, RegistrarProductoDto dto);

    Producto buscarPorId(long id);

    Producto guardar(Producto producto);

    void eliminar(long id);

    void reactivar(long id);


    
}
