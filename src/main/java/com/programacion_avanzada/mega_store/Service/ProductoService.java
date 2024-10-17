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
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;
import com.programacion_avanzada.mega_store.Repository.ProductoRepository;
import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;

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
    SubCategoriaRepository subCategoriaRepository;

    @Autowired
    MarcaRepository marcaRepository;

    /*
     * Metodo encargado de registrar el producto, verificando que el nombre no exista,
     * luego se encarga de nombalizar los datos y asignar que se encuentra activo.
     */
    @Override
    public RegistrarProductoDto registrarProducto(RegistrarProductoDto dto) {
    
        // verificamos que el producto no no exista.
        if (!productoRepository.existsByNombre(dto.getNombre())) {
            Producto producto = productoMapper.toEntity(dto);
            
            // Normarlizamos los atributos del producto.
            producto.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase().trim()));
            producto.setDescripcion(StringUtil.capitalizeFirstLetter(dto.getDescripcion().toLowerCase().trim()));
            producto.setColor(StringUtil.capitalizeFirstLetter(dto.getColor().toLowerCase()).trim());
            producto.setTamano(StringUtil.capitalizeFirstLetter(dto.getTamano().toLowerCase().trim()));
            producto.setStock(dto.getStock());
            producto.setPrecioUnitario(dto.getPrecioUnitario());

            //Asignamos el estado como activo.
            producto.setEstaActivo(true);
            
            //Buscamos la marca y se la asignamos.
            Marca marca = marcaRepository.findById(dto.getMarcaId()).orElseThrow();
            producto.setMarca(marca);

            //Buscamos la categoria y se la asignamos.(Hay que cambiarlo)
            SubCategoria subCategoria = subCategoriaRepository.findById(dto.getSubCategoriaId()).orElseThrow();
            producto.setSubcategoria(subCategoria);
    

            return registrarProductoMapper.toDto(productoRepository.save(producto));
        }
        
        return null; //deberia salir una excepcion.
    }

    /*
     * Metodo que para listar todos los productos que se encuentren activos.
     */
    @Override
    public List<ProductoDto> listar() {
        List<Producto> productos = productoRepository.findAllByEstaActivoIsTrue();
        return  productos.stream().map(productoMapper::toDto).toList();
    }

   
    /*
     * Metodo que elimina el producto, asignandole un estado false.
     */
    @Override
    public void eliminar(long id) {
        Producto producto = productoRepository.findById(id).filter(Producto::isEstaActivo).orElse(null);
        if(producto != null){
            producto.setEstaActivo(false);
            productoRepository.save(producto);
        }
        
    }

    /*
     * Meotodo encargado de editar los productos, verificando si se encuentra activo.
     * Comparte los mismos atributos que el DTO para registrar el producto.
     */
    @Override
    public RegistrarProductoDto editarProducto(long id,RegistrarProductoDto dto) {

        Producto producto = productoRepository.findById(id).filter(Producto::isEstaActivo).orElse(null);
        
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

        /*
         * Los 3 condicionales verifican que no se ingresen numeros negativos.
         */
        if(dto.getStock() > 0){
            producto.setStock(dto.getStock());
        }

        if(dto.getUmbralBajoStock() > 0){
            producto.setUmbralBajoStock(dto.getUmbralBajoStock());
        }

        if(dto.getMarcaId() > 0){
           Marca marca = marcaRepository.findById(dto.getMarcaId()).orElse(null);
           producto.setMarca(marca);
        }

        //Falta sub categoria.

        return registrarProductoMapper.toDto(productoRepository.save(producto));
    }


    @Override
    public void reactivar(long id){
        Producto producto = productoRepository.findById(id).orElse(null);
        if(producto != null && producto.isEstaActivo() == false){
            producto.setEstaActivo(true);
            productoRepository.save(producto);
        }
    }


    
}
