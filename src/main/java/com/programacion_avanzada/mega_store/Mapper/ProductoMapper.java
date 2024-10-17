package com.programacion_avanzada.mega_store.Mapper;

import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    // Convierte Producto entity a ProductoDto
    ProductoDto toDto(Producto producto);
    
    // Convierte RegistrarProductoDto a Producto entity
    Producto toEntity(RegistrarProductoDto registrarProductoDto);

    // Convierte Categoria entity a CategoriaDto
    SubCategoriaDto toDto(SubCategoria subCategoria);
    
    // Convierte CategoriaDto a Categoria entity
    SubCategoria toEntity(SubCategoriaDto subCategoriaDto);

    // Convierte Marca entity a MarcaDto
    MarcaDto toDto(Marca marca);
    
    // Convierte MarcaDto a Marca entity
    Marca toEntity(MarcaDto marcaDto);
}
