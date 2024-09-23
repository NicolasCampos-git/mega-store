package com.programacion_avanzada.mega_store.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.Modelos.Producto;

@Mapper(componentModel = "spring", uses = {MarcaMapper.class, CategoriaMapper.class})
public interface ProductoMapper {

    @Mapping(source = "marca", target = "marca")
    @Mapping(source = "categoria", target = "categoria")
    ProductoDto toDto(Producto producto);


    @Mapping(source = "marca", target = "marca")
    @Mapping(source = "categoria", target = "categoria")
    Producto toEntity(ProductoDto productoDto);
}