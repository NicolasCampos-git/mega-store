package com.programacion_avanzada.mega_store.Mapper.MarcaMappers;

import org.mapstruct.Mapper;

import com.programacion_avanzada.mega_store.DTOs.MarcaDtos.MarcaDto;
import com.programacion_avanzada.mega_store.Modelos.Marca;

@Mapper(componentModel = "spring")
public interface MarcaMapper {
    Marca toEntity(MarcaDto marcaDto);

    MarcaDto toDto(Marca marca);
}
