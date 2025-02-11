package com.programacion_avanzada.mega_store.Mapper.SubcategoriaMappers;

import org.mapstruct.Mapper;

import com.programacion_avanzada.mega_store.DTOs.SubcategoriaDtos.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;

@Mapper(componentModel = "spring")
public interface RegistrarSubCategoriaMapper {
    RegistrarSubCategoriaDto toDto(SubCategoria subCategoria);

    SubCategoria toEntity(RegistrarSubCategoriaDto dto);
    
} 