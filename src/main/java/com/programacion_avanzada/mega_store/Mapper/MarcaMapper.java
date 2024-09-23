package com.programacion_avanzada.mega_store.Mapper;

import org.mapstruct.Mapper;

import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.Modelos.Marca;

@Mapper(componentModel = "spring")
public interface MarcaMapper {

    
    
    MarcaDto toDto(Marca marca);
    Marca toEntity(MarcaDto marcaDto);
    
}