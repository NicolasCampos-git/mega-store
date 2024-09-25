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

    // Convierte Producto entity a ProductoDto
    ProductoDto toDto(Producto producto);
    
    // Convierte RegistrarProductoDto a Producto entity
    Producto toEntity(RegistrarProductoDto registrarProductoDto);

    // Convierte Categoria entity a CategoriaDto
    CategoriaDto toDto(Categoria categoria);
    
    // Convierte CategoriaDto a Categoria entity
    Categoria toEntity(CategoriaDto categoriaDto);

    // Convierte Marca entity a MarcaDto
    MarcaDto toDto(Marca marca);
    
    // Convierte MarcaDto a Marca entity
    Marca toEntity(MarcaDto marcaDto);
}
