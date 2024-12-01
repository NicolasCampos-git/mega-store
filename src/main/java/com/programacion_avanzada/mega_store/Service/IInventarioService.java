package com.programacion_avanzada.mega_store.Service;



import com.programacion_avanzada.mega_store.DTOs.stockDTO;
import com.programacion_avanzada.mega_store.Modelos.Producto;

public interface IInventarioService {
    
    Producto agregarStock(long id, stockDTO stock);

    Producto quitarStock(long id, stockDTO stock);
}
