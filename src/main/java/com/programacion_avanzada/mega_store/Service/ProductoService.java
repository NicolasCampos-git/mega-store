package com.programacion_avanzada.mega_store.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.Mapper.RegistrarProductoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
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
    RegistrarProductoMapper registrarProductoMapper;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Override
    public RegistrarProductoDto registrarProducto(RegistrarProductoDto dto) {
    
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
            
            Marca marca = marcaRepository.findById(dto.getMarcaId()).orElseThrow();
            producto.setMarca(marca);

            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElseThrow();
            producto.setCategoria(categoria);
    

            return registrarProductoMapper.toDto(productoRepository.save(producto));
        }
        
        return null; //deberia salir una excepcion.
    }

    @Override
    public List<ProductoDto> listar() {
        List<Producto> productos = productoRepository.findAllByEstaActivoIsTrue();
        return  productos.stream().map(productoMapper::toDto).toList();
    }

   

    @Override
    public void eliminar(long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if(producto != null){
            producto.setEstaActivo(false);
            productoRepository.save(producto);
        }
        
    }

    @Override
    public RegistrarProductoDto editarProducto(long id,RegistrarProductoDto dto) {

        Producto producto = productoRepository.findById(id).orElse(null);
        
        if(dto.getNombre() != null){
            producto.setNombre(dto.getNombre());
        }

        if(dto.getDescripcion() != null){
            producto.setDescripcion(dto.getDescripcion());
        }

        if(dto.getTamano() != null){
            producto.setTamano(dto.getTamano());
        }

        if(dto.getColor() != null ){
            producto.setColor(dto.getColor());
        }

        //Deuda tecnica
        if(dto.getStock() != 0){
            producto.setStock(dto.getStock());
        }

        if(dto.getUmbralBajoStock() != 0){
            producto.setUmbralBajoStock(dto.getUmbralBajoStock());
        }

        if(dto.getMarcaId() != 0){
           Marca marca = marcaRepository.findById(dto.getMarcaId()).orElse(null);
           producto.setMarca(marca);
        }

        return registrarProductoMapper.toDto(productoRepository.save(producto));
    }

    


    
}
