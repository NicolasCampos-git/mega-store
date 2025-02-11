package com.programacion_avanzada.mega_store.Mapper.CategoriaMappers;

import org.mapstruct.Mapper;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDtos.CategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    Categoria toEntity(CategoriaDto dto);

    CategoriaDto toDto(Categoria categoria);
}
