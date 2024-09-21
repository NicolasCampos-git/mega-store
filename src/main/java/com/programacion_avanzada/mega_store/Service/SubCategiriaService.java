package com.programacion_avanzada.mega_store.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.SubCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;


public class SubCategiriaService {
    
    @Autowired
    SubCategoriaRepository subCategoriaRepository;

    @Autowired
    SubCategoriaMapper subCategoriaMapper;

    public SubCategoriaDto registrarCategiria(SubCategoriaDto dto) {
        SubCategoria subCategoria = subCategoriaMapper.toEntity(dto);
        return subCategoriaMapper.toDto(subCategoriaRepository.save(subCategoria));
    }

    public List<SubCategoriaDto> listar(){
        
        try{
            List<SubCategoria> subCategorias = subCategoriaRepository.findAll();
            
            return subCategorias.stream().map(subCategoriaMapper::toDto).toList();

        }catch(Exception e){
            throw new RuntimeException("Error al listar subcategorías", e);
        }
        
    }

    public void eliminar(SubCategoria subCategoria){
        subCategoria.setEstaActivo(false);
        subCategoriaRepository.save(subCategoria);
    }
    
}
