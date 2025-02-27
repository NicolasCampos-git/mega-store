package com.programacion_avanzada.mega_store.Mapper.SubcategoriaMappers;





import org.mapstruct.Mapper;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDtos.CategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.SubcategoriaDtos.SubCategoriaDTO;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;





@Mapper(componentModel = "spring")
public interface SubCategoriaMapper {
    
    SubCategoria toEntity(SubCategoriaDTO subCategoriaDto);
    SubCategoriaDTO toDto(SubCategoria subCategoria);

    Categoria toEntity(CategoriaDto categoriaDto);
    CategoriaDto toDto(Categoria categoria);
}