package com.programacion_avanzada.mega_store.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.DTOs.stockDTO;
import com.programacion_avanzada.mega_store.Modelos.Producto;

@Service
public class InventarioService implements IInventarioService{
    
    @Autowired
    private IProductoService productoService;

    

    @Autowired
    SenderService senderService;
    
    @Override
        public Producto agregarStock(long idProducto, stockDTO stock){
        Producto producto = productoService.buscarPorId(idProducto);
        if(stock.getCantidad() <= 0 || stock.getCantidad() > 999999){
            throw new IllegalArgumentException("La cantidad debe estar entre 1 y 999999.");
        }
        producto.setStock(producto.getStock() + stock.getCantidad());
        if (producto.getStock() > 999999) {
            throw new IllegalArgumentException("El stock no puede ser mayor a 999999.");
        }
        
        return productoService.guardar(producto);
        
    }

    @Override
    public Producto quitarStock(long idProducto, stockDTO stock){
        Producto producto = productoService.buscarPorId(idProducto);
        if(stock.getCantidad() <= 0 || stock.getCantidad() > producto.getStock()){
            throw new IllegalArgumentException("La cantidad debe estar entre 1 y el maximo stock.");
        }
        producto.setStock(producto.getStock()-stock.getCantidad());
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser menor a 0.");
        }
        
        
        return productoService.guardar(producto);
    }

    @Scheduled(fixedRate = 60000)
    public void revisarStock(){
        List<ProductoDto> productos = productoService.listar();

        for(ProductoDto producto: productos){
            if(producto.getStock() < producto.getUmbralBajoStock()){
                senderService.notificarBajoStock(producto.getId());
            }

        }
        
    }
    
}
