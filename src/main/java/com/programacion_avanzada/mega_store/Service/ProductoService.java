package com.programacion_avanzada.mega_store.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.Mapper.CategoriaMapper;
import com.programacion_avanzada.mega_store.Mapper.MarcaMapper;
import com.programacion_avanzada.mega_store.Mapper.ProductoMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;
import com.programacion_avanzada.mega_store.Repository.ProductoRepository;

import ch.qos.logback.core.util.StringUtil;

@Service
public class ProductoService implements IProductoService {
    
    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ProductoMapper productoMapper;

    @Autowired
    CategoriaMapper categoriaMapper;

    @Autowired
    MarcaMapper marcaMapper;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Override
    public ProductoDto registrarProducto(ProductoDto dto) {
    
        // Verificar si el producto ya existe por nombre
        if (!productoRepository.existsByNombre(dto.getNombre())) {
            Producto producto = productoMapper.toEntity(dto);
            
            // Establecer atributos del producto
            producto.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase()));
            producto.setDescripcion(StringUtil.capitalizeFirstLetter(dto.getDescripcion().toLowerCase()));
            producto.setColor(StringUtil.capitalizeFirstLetter(dto.getColor().toLowerCase()));
            producto.setTamano(StringUtil.capitalizeFirstLetter(dto.getTamano().toLowerCase()));
            producto.setStock(dto.getStock());
            producto.setPrecioUnitario(dto.getPrecioUnitario());
            producto.setEstaActivo(true);
            
            // Mapear Marca y Categoria
            //Marca marca = marcaMapper.toEntity(dto.getMarca());
            Categoria categoria = categoriaMapper.toEntity(dto.getCategoria());
    
            // // Validar si la marca y la categoría existen
            // if (!marcaRepository.existsByNombre(marca.getNombre())) {
            //     throw new IllegalArgumentException("La marca no existe.");
            // }
            // if (!categoriaRepository.existsByNombre(categoria.getNombre())) {
            //     throw new IllegalArgumentException("La categoría no existe.");
            // }
    
            // Asignar Marca y Categoria al producto
            //producto.setMarca(marca);
            producto.setCategoria(categoria);
    
            // Guardar el producto y devolver el DTO
            return productoMapper.toDto(productoRepository.save(producto));
        }
        
        return null; // o lanzar una excepción si el producto ya existe
    }

    @Override
    public List<ProductoDto> listar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }

    @Override
    public ProductoDto editarProducto(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editarProducto'");
    }

    @Override
    public void eliminar(Producto producto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    


    
}
