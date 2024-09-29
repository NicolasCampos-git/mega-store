package com.programacion_avanzada.mega_store.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.CategoriaMapper;
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

    /*
     * Metodo para registrar una nueva categoria, verifica que si nombre ya existe.
     * Al momento de guardarse las categorias, sus nombres y descripcion quedan normalizados.
     */
    @Override
    public CategoriaDto registrarCategoria(CategoriaDto dto) {
        
        Categoria categoria = categoriaMapper.toEntity(dto);
        
        if(categoriaRepository.existsByNombre(categoria.getNombre()) == false){

            categoria.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase()));
            categoria.setDescripcion(dto.getDescripcion().toLowerCase());
            return categoriaMapper.toDto(categoriaRepository.save(categoria));

        }else{
            throw new EntityExistsException("La categoria ya existe");
        }
    }

    /*
     * Metodo que lista todas las categorias existentes que esten activas
     */
    
    public List<CategoriaDto> listar() {
        List<Categoria> categorias = categoriaRepository.findAllByEstaActivoIsTrue();
        return categorias.stream().map(categoriaMapper::toDto).toList();
    }

    /*
     * Metodo encargado de la busqueda de categorias por id,
     * verificando que se encuentran activas.
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
        categoria.setEstaActivo(false);
        categoriaRepository.save(categoria);
    }
}
