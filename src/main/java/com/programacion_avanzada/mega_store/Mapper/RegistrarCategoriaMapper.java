package com.programacion_avanzada.mega_store.Mapper;

import org.mapstruct.Mapper;


import com.programacion_avanzada.mega_store.DTOs.RegistrarCategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.Categoria;

@Mapper(componentModel = "spring")
public interface RegistrarCategoriaMapper{

    RegistrarCategoriaDto toDto(Categoria categoria);
    Categoria toEntity(RegistrarCategoriaDto dto);

    
}
