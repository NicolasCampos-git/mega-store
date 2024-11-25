package com.programacion_avanzada.mega_store.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.Mapper.RegistrarProductoMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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
import lombok.val;

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
        // Primero, verifica si la marca y la subcategoría existen
        if (!marcaRepository.existsById(dto.getMarcaId())) {
            throw new EntityNotFoundException("La marca no existe");
        }
        if (!subCategoriaRepository.existsById(dto.getSubCategoriaId())) {
            throw new EntityNotFoundException("La subcategoría no existe");
        }


        // verificamos que el producto no no exista.
        if (!productoRepository.existsByNombre(dto.getNombre())) {
            Producto producto = registrarProductoMapper.toEntity(dto);

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

        throw new EntityExistsException("El producto ya existe"); //deberia salir una excepcion.
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
        
        validarNombre(producto.getNombre());

        validarDescripcion(producto.getDescripcion());

        validarTamano(producto.getTamano());

        validarColor(producto.getColor());

        valirdarPrecio(producto.getPrecioUnitario());

        validarStock(producto.getStock());

        validarUmbralBajoStock(producto.getUmbralBajoStock());

        validarStockYUmbralBajoStock(producto.getStock(), producto.getUmbralBajoStock());

        validarMarca(dto.getMarcaId(), producto);

        ValidarSubCategoria(dto.getSubCategoriaId(), producto);

        normalizarDatos(producto);

        

        return registrarProductoMapper.toDto(guardar(producto));
    }

    @Override
    public Producto buscarPorId(long id){
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto == null) {
            throw new EntityNotFoundException("El producto no existe.");
        }
        return null;
    }



    @Override
    public void reactivar(long id){
        Producto producto = productoRepository.findById(id).orElse(null);
        if(producto != null && producto.isEstaActivo() == false){
            producto.setEstaActivo(true);
            guardar(producto);
        }
    }

    private void validarNombre(String nombre){
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
            
        }
        if (nombre.length() < 2 || nombre.length() > 64) {
            throw new IllegalArgumentException("El nombre del producto debe tener entre 2 y 64 caracteres.");
        }
        if (nombre.contains(" ")) {
            throw new IllegalArgumentException("El nombre del producto no debe contener espacios.");
            
        }
    }

    private void validarDescripcion(String descripcion){
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripcion del producto no puede estar vacía.");
            
        }
        if (descripcion.length() < 2 || descripcion.length() > 64) {
            throw new IllegalArgumentException("La descripcion del producto debe tener entre 2 y 64 caracteres.");
        }
        if (descripcion.contains(" ")) {
            throw new IllegalArgumentException("La descripcion del producto no debe contener espacios.");
            
        }
    }

    private void validarTamano(String tamano){
        if (tamano == null || tamano.isEmpty()) {
            throw new IllegalArgumentException("El tamaño del producto no puede estar vacío.");
            
        }
        if (tamano.length() < 2 || tamano.length() > 3) {
            throw new IllegalArgumentException("Solo se permite XS,S,M,L, XL O XXL para el tamaño del producto.");
        }
        if (tamano.contains(" ")) {
            throw new IllegalArgumentException("El tamaño del producto no debe contener espacios.");
            
        }
    }

    private void validarColor(String color){
        if (color == null || color.isEmpty()) {
            throw new IllegalArgumentException("El color del producto no puede estar vacío.");
            
        }
        if (color.length() < 2 || color.length() > 64) {
            throw new IllegalArgumentException("El color del producto debe tener entre 2 y 64 caracteres.");
        }
        
    }

    private void valirdarPrecio(double precioUnitario){
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("El precio unitario del producto debe ser mayor a 0.");
        }

        if (precioUnitario > 999999.99) {
            throw new IllegalArgumentException("El precio unitario del producto debe ser menor a 999999.99.");
            
        }

        if (precioUnitario == 0) {
            throw new IllegalArgumentException("El precio unitario del producto no puede estar vacío.");
            
        }

    }

    private void validarStock(int stock){
        if (stock < 0) {
            throw new IllegalArgumentException("El stock del producto debe ser mayor a 0.");
        }

        if (stock > 999) {
            throw new IllegalArgumentException("El stock del producto debe ser menor a 999999.");
            
        }

        if (stock == 0) {
            throw new IllegalArgumentException("El stock del producto no puede estar vacío.");
            
        }
    }

    private void validarUmbralBajoStock(int umbralBajoStock){
        if (umbralBajoStock < 0) {
            throw new IllegalArgumentException("El umbral bajo de stock del producto debe ser mayor a 0.");
        }

        if (umbralBajoStock > 999) {
            throw new IllegalArgumentException("El umbral bajo de stock del producto debe ser menor a 999999.");
            
        }
        if (umbralBajoStock == 0) {
            throw new IllegalArgumentException("El umbral bajo de stock del producto no puede estar vacío.");
            
        }

        
    }

    private void validarStockYUmbralBajoStock(int stock, int umbralBajoStock){
        if (stock < umbralBajoStock) {
            throw new IllegalArgumentException("El stock del producto debe ser mayor o igual al umbral bajo de stock.");
        }
    }

    private void validarMarca(long marcaId, Producto producto){
        if (marcaId <= 0) {
            throw new IllegalArgumentException("La marca del producto no puede estar vacía.");
        }

        Marca marca = marcaRepository.findById(marcaId).orElse(null);
        if (marca == null) {
            throw new IllegalArgumentException("La marca del producto no existe.");
        }
        producto.setMarca(marca);

        
    }

    private void ValidarSubCategoria(long subCategoriaId, Producto producto){
        if (subCategoriaId <= 0) {
            throw new IllegalArgumentException("La subcategoria del producto no puede estar vacía.");
        }

        SubCategoria subCategoria = subCategoriaRepository.findById(subCategoriaId).orElse(null);
        if (subCategoria == null) {
            throw new IllegalArgumentException("La subcategoria del producto no existe.");
        }
        producto.setSubcategoria(subCategoria);
    }

    private void normalizarDatos(Producto producto){
        producto.setNombre(producto.getNombre().toLowerCase().trim());
        producto.setDescripcion(producto.getDescripcion().toLowerCase().trim());
        producto.setColor(producto.getColor().toLowerCase().trim());
        producto.setTamano(producto.getTamano().toUpperCase().trim());

    }

    @Override
    public Producto guardar(Producto producto){
        return productoRepository.save(producto);
    }

    


    
}
