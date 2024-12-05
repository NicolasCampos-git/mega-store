package com.programacion_avanzada.mega_store.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.CategoriaMapper;
import com.programacion_avanzada.mega_store.Mapper.RegistrarCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;

import ch.qos.logback.core.util.StringUtil;
import jakarta.persistence.EntityExistsException;

@Service
public class CategoriaService  implements ICategoriaService{

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Autowired
    private RegistrarCategoriaMapper registrarCategoriaMapper;

    /*
     * Metodo para registrar una nueva categoria, verifica si nombre ya existe.
     * Al momento de guardarse las categorias, sus nombres y descripcion quedan normalizados.
     */
    @Override
    public RegistrarCategoriaDto registrarCategoria( RegistrarCategoriaDto dto) {
        
        Categoria categoria = registrarCategoriaMapper.toEntity(dto);
        
        if(categoriaRepository.existsByNombre(categoria.getNombre()) == true){
            throw new EntityExistsException("La categoria ya existe");
        }
        validarNombre(categoria.getNombre());
        validarDescripcion(categoria.getDescripcion());
        categoria.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase().trim()));
        categoria.setDescripcion(dto.getDescripcion().toLowerCase().trim());
        categoria.setEstaActivo(true);
        return registrarCategoriaMapper.toDto(categoriaRepository.save(categoria));
    }

    /*
     * Metodo que lista todas las categorias existentes
     */
    @Override
    public List<CategoriaDto> listar() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream().map(categoriaMapper::toDto).toList();
    }

    /*
     * Metodo encargado de la busqueda de categorias por id
     */
    @Override
    public Categoria buscarPorId(long id) {
        return categoriaRepository.findById(id).filter(Categoria::isEstaActivo).orElse(null);
    }


    /*
     * Elimina las categorias por id, filtrando las que no estan activas.
     */
    @Override
    public void eliminar(long id) {
        Categoria categoria = categoriaRepository.findById(id).filter(Categoria::isEstaActivo).orElse(null);
        if(categoria != null){
            categoria.setEstaActivo(false);
            categoriaRepository.save(categoria);
        }
    }

    @Override
    public CategoriaDto actualizar(long id, CategoriaDto dto) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        
        
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoria);
    }

    @Override
    public void reactivar(long id){
        Categoria categoria = categoriaRepository.findById(id).orElse(null);

        if(categoria != null && categoria.isEstaActivo() == false){
            categoria.setEstaActivo(true);
            categoriaRepository.save(categoria);
        }

    }

    public void validarNombre(String nombre){
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
            
        }
        if (nombre.length() < 2 || nombre.length() > 64) {
            throw new IllegalArgumentException("El nombre de la categoría debe tener entre 2 y 64 caracteres.");
        }
        if (nombre.contains(" ")) {
            throw new IllegalArgumentException("El nombre de la categoría no debe contener espacios.");
            
        }
        if (nombre.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El nombre no debe contener números.");
        }

    }

    public void validarDescripcion(String descripcion){
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripción de la categoría no puede estar vacía.");
        }
        if (descripcion.length() < 2 || descripcion.length() > 100) {
            throw new IllegalArgumentException("La descripción de la categoría debe tener entre 2 y 100 caracteres.");
        }
        if (descripcion.matches(".*\\d.*")) { // Verifica si contiene números
            throw new IllegalArgumentException("La descripción no puede contener números.");
        }
    }

}
