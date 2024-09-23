package com.programacion_avanzada.mega_store.Mapper;

import org.mapstruct.Mapper;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;

import com.programacion_avanzada.mega_store.Modelos.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaDto toDto(Categoria categoria);
    Categoria toEntity(CategoriaDto categoriaDTO);

    
}
