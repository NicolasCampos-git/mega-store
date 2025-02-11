package com.programacion_avanzada.mega_store.Service;

import java.util.List;

import com.programacion_avanzada.mega_store.Mapper.ProductoMappers.ProductoMapper;
import com.programacion_avanzada.mega_store.Mapper.ProductoMappers.RegistrarProductoMapper;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.ProductoDtos.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;
import com.programacion_avanzada.mega_store.Repository.ProductoRepository;
import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;
import com.programacion_avanzada.mega_store.Service.Interfaces.IProductoService;

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
     * luego se encarga de normalizar los datos y asignar que se encuentra activo.
     */
    @Override
    public Producto registrarProducto(RegistrarProductoDto dto) {
        // Primero, verifica si la marca y la subcategoría existen
        if (!marcaRepository.existsById(dto.getMarcaId())) {
            throw new EntityNotFoundException("La marca no existe");
        }
        if (!subCategoriaRepository.existsById(dto.getSubCategoriaId())) {
            throw new EntityNotFoundException("La subcategoría no existe");
        }


        // verificamos que el producto no exista.
        if (!productoRepository.existsByNombre(dto.getNombre())) {
            Producto producto = registrarProductoMapper.toEntity(dto);

            // Normarlizamos los atributos del producto.
            validarNombre(dto.getNombre());
            validarDescripcion(producto.getDescripcion());

            validarTamano(producto.getTamano());
    
            validarColor(producto.getColor());
    
            valirdarPrecio(producto.getPrecioUnitario());
    
            validarStock(producto.getStock());
    
            validarUmbralBajoStock(producto.getUmbralBajoStock());
    
            validarStockYUmbralBajoStock(producto.getStock(), producto.getUmbralBajoStock());
    
            validarMarca(dto.getMarcaId(), producto);
    
            validarSubCategoria(dto.getSubCategoriaId(), producto);

            producto.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase().trim()));
            producto.setDescripcion(StringUtil.capitalizeFirstLetter(dto.getDescripcion().toLowerCase().trim()));
            producto.setColor(StringUtil.capitalizeFirstLetter(dto.getColor().toLowerCase()).trim());
            producto.setTamano(StringUtil.capitalizeFirstLetter(dto.getTamano().toLowerCase().trim()));
            producto.setStock(dto.getStock());
            producto.setPrecioUnitario(dto.getPrecioUnitario());
            normalizarDatos(producto);

            //Asignamos el estado como activo.
            producto.setEstaActivo(true);

            //Buscamos la marca y se la asignamos.
            Marca marca = marcaRepository.findById(dto.getMarcaId()).orElseThrow();
            producto.setMarca(marca);

            //Buscamos la categoria y se la asignamos.
            SubCategoria subCategoria = subCategoriaRepository.findById(dto.getSubCategoriaId()).orElseThrow();
            producto.setSubcategoria(subCategoria);


            return productoRepository.save(producto);
        }

        throw new EntityExistsException("El producto ya existe"); //deberia salir una excepcion.
    }


    /*
     * Metodo que para listar todos los productos
     */
    @Override
    public List<Producto> listar() {
        return productoRepository.findAll();
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
     * Meotodo encargado de editar los productos
     * Comparte los mismos atributos que el DTO para registrar el producto.
     */
    @Override
    public Producto editarProducto(long id,RegistrarProductoDto dto) {

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

        validarSubCategoria(dto.getSubCategoriaId(), producto);

        normalizarDatos(producto);

        productoRepository.save(producto);
        // Guardar el producto actualizado
        Producto productoGuardado = guardar(producto);

        return productoGuardado;
    }


    @Override
    public Producto buscarPorId(long id){
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto == null) {
            throw new EntityNotFoundException("El producto no existe.");
        }
        return producto;
    }



    @Override
    public void reactivar(long id){
        Producto producto = productoRepository.findById(id).orElse(null);
        if(producto != null && producto.isEstaActivo() == false){
            producto.setEstaActivo(true);
            guardar(producto);
        }
    }

    public void validarNombre(String nombre){
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
            
        }
        if (nombre.length() < 2 || nombre.length() > 64) {
            throw new IllegalArgumentException("El nombre del producto debe tener entre 2 y 64 caracteres.");
        }
        if (nombre.contains(" ")) {
            throw new IllegalArgumentException("El nombre del producto no debe contener espacios.");
        }
        if (nombre.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El nombre no debe contener números.");
        }
    }

    public void validarDescripcion(String descripcion){
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripcion del producto no puede estar vacía.");
            
        }
        if (descripcion.length() < 2 || descripcion.length() > 64) {
            throw new IllegalArgumentException("La descripcion del producto debe tener entre 2 y 64 caracteres.");
        }
        if (descripcion.matches(".*\\d.*")) {
            throw new IllegalArgumentException("La descripcion no debe contener números.");
        }
        
    }

    public void validarTamano(String tamano){
        if (tamano == null || tamano.isEmpty()) {
            throw new IllegalArgumentException("El tamaño del producto no puede estar vacío.");
            
        }
        if (tamano.length() < 1 || tamano.length() > 3) {
            throw new IllegalArgumentException("Solo se permite XS,S,M,L, XL O XXL para el tamaño del producto.");
        }
        if (tamano.contains(" ")) {
            throw new IllegalArgumentException("El tamaño del producto no debe contener espacios.");
            
        }
    }

    public void validarColor(String color){
        if (color == null || color.isEmpty()) {
            throw new IllegalArgumentException("El color del producto no puede estar vacío.");
            
        }
        if (color.length() < 2 || color.length() > 5) {
            throw new IllegalArgumentException("El color del producto debe tener entre 2 y 5 caracteres.");
        }
        if (color.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El color no debe contener números.");
        }
        
    }

    public void valirdarPrecio(double precioUnitario){
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

    public void validarStock(int stock){
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

    public void validarUmbralBajoStock(int umbralBajoStock){
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

    public void validarStockYUmbralBajoStock(int stock, int umbralBajoStock){
        if (stock < umbralBajoStock) {
            throw new IllegalArgumentException("El stock del producto debe ser mayor o igual al umbral bajo de stock.");
        }
    }

    public void validarMarca(long marcaId, Producto producto){
        if (marcaId <= 0) {
            throw new IllegalArgumentException("La marca del producto no puede estar vacía.");
        }

        Marca marca = marcaRepository.findById(marcaId).orElse(null);
        if (marca == null) {
            throw new IllegalArgumentException("La marca del producto no existe.");
        }
        producto.setMarca(marca);

        
    }

    public void validarSubCategoria(long subCategoriaId, Producto producto) {
        if (subCategoriaId <= 0) {
            throw new IllegalArgumentException("La subcategoria del producto no puede estar vacía.");
        }
    
        SubCategoria subCategoria = subCategoriaRepository.findById(subCategoriaId).orElse(null);
        if (subCategoria == null) {
            throw new IllegalArgumentException("La subcategoria del producto no existe.");
        }
        producto.setSubcategoria(subCategoria);
    }

    public void normalizarDatos(Producto producto){
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
