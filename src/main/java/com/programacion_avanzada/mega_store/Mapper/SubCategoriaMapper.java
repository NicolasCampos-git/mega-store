package com.programacion_avanzada.mega_store.Mapper;

import org.mapstruct.Mapper;

import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;

@Mapper
public interface SubCategoriaMapper {

    SubCategoriaDto toDto(SubCategoria subCategoria);
    SubCategoria toEntity(SubCategoriaDto subCategoriaDto);
}

