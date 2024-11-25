package com.programacion_avanzada.mega_store.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.Modelos.Producto;

@Service
public class InventarioService implements IInventarioService{
    
    @Autowired
    private IProductoService productoService;
    
    @Override
        public Producto agregarStock(long idProducto, int cantidad){
        Producto producto = productoService.buscarPorId(idProducto);
        if(cantidad <= 0 || cantidad > 999999){
            throw new IllegalArgumentException("La cantidad debe estar entre 1 y 999999.");
        }
        producto.setStock(producto.getStock() + cantidad);
        if (producto.getStock() > 999999) {
            throw new IllegalArgumentException("El stock no puede ser mayor a 999999.");
        }
        producto.setStock(producto.getStock() - cantidad);
        return productoService.guardar(producto);
        
    }

    @Override
    public Producto quitarStock(long idProducto, int cantidad){
        Producto producto = productoService.buscarPorId(idProducto);
        if(cantidad <= 0 || cantidad > producto.getStock()){
            throw new IllegalArgumentException("La cantidad debe estar entre 1 y el maximo stock.");
        }
        producto.setStock(producto.getStock()-cantidad);
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser menor a 0.");
        }
        producto.setStock(producto.getStock()+cantidad);
        
        return productoService.guardar(producto);
    }
    
}
