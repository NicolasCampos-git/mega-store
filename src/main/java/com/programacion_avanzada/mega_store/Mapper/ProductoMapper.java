package com.programacion_avanzada.mega_store.Mapper;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoDto toDto(Producto producto);
    Producto toEntity(RegistrarProductoDto productoDto);

    CategoriaDto toDto(Categoria categoria);
    Categoria toEntity(CategoriaDto categoriaDTO);

    MarcaDto toDto(Marca marca);
    Marca toEntity(MarcaDto marcaDto);

}
