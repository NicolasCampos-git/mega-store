package com.programacion_avanzada.mega_store.Mapper;



import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import org.springframework.stereotype.Component;

@Component
public class RegistrarProductoMapper {

    // Convierte de ProductoDto a Producto
    public Producto toEntity(RegistrarProductoDto registrarProductoDto) {
        Producto producto = new Producto();
        producto.setNombre(registrarProductoDto.getNombre());
        producto.setDescripcion(registrarProductoDto.getDescripcion());
        producto.setTamano(registrarProductoDto.getTamano());
        producto.setColor(registrarProductoDto.getColor());
        producto.setUrlImagen(registrarProductoDto.getUrlImagen());
        producto.setPrecioUnitario(registrarProductoDto.getPrecioUnitario());
        producto.setStock(registrarProductoDto.getStock());
        producto.setUmbralBajoStock(registrarProductoDto.getUmbralBajoStock());
        return producto;
    }

    // Convierte de Producto a ProductoDto
    public RegistrarProductoDto toDto(Producto producto) {
        RegistrarProductoDto registrarProductoDto = new RegistrarProductoDto();
        registrarProductoDto.setNombre(producto.getNombre());
        registrarProductoDto.setDescripcion(producto.getDescripcion());
        registrarProductoDto.setTamano(producto.getTamano());
        registrarProductoDto.setColor(producto.getColor());
        registrarProductoDto.setUrlImagen(producto.getUrlImagen());
        registrarProductoDto.setPrecioUnitario(producto.getPrecioUnitario());
        registrarProductoDto.setStock(producto.getStock());
        registrarProductoDto.setUmbralBajoStock(producto.getUmbralBajoStock());
        return registrarProductoDto;
    }
}