package com.programacion_avanzada.mega_store.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.SubCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;

import ch.qos.logback.core.util.StringUtil;

@Service
public class SubCategoriaService implements ISubCategoriaService {

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    @Autowired
    private SubCategoriaMapper subCategoriaMapper;

    @Override
    public SubCategoriaDto registrarCategoria(SubCategoriaDto dto) {
        
        //Falta buscar un nombre igual para verificar.
        SubCategoria subCategoria = subCategoriaMapper.toEntity(dto);
        subCategoria.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase()));
        subCategoria.setDescripcion(dto.getDescripcion().toLowerCase());
        return subCategoriaMapper.toDto(subCategoriaRepository.save(subCategoria));
    }

    
    public List<SubCategoriaDto> listar() {
        List<SubCategoria> subCategorias = subCategoriaRepository.findAll();
        return subCategorias.stream().map(subCategoriaMapper::toDto).toList();
    }

    @Override
    public Optional<SubCategoria> buscarPorId(long id) {
        return subCategoriaRepository.findById(id);
    }

    @Override
    public void eliminar(SubCategoria subCategoria) {
        subCategoria.setEstaActivo(false);
        subCategoriaRepository.save(subCategoria);
    }
}
