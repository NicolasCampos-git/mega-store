package com.programacion_avanzada.mega_store.Mapper;

import org.mapstruct.Mapper;


import com.programacion_avanzada.mega_store.DTOs.RegistrarMarcaDto;
import com.programacion_avanzada.mega_store.Modelos.Marca;

@Mapper(componentModel = "spring")
public interface RegistrarMarcaMapper {
    
    RegistrarMarcaDto toDto(Marca marca);
    Marca toEntity(RegistrarMarcaDto marcaDto);
    
}