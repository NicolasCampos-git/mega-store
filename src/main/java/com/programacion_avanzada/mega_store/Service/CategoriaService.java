package com.programacion_avanzada.mega_store.Service;

import java.util.List;
import java.util.Optional;

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

    
    public List<CategoriaDto> listar() {
        List<Categoria> subCategorias = categoriaRepository.findAll();
        return subCategorias.stream().map(categoriaMapper::toDto).toList();
    }

    @Override
    public Optional<Categoria> buscarPorId(long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public void eliminar(Categoria categoria) {
        categoria.setEstaActivo(false);
        categoriaRepository.save(categoria);
    }
}
