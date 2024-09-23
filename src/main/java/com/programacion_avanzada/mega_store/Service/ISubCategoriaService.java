package com.programacion_avanzada.mega_store.Service;

import java.util.List;
import java.util.Optional;

import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;

public interface ISubCategoriaService {
    SubCategoriaDto registrarCategoria(long categoriaId,SubCategoriaDto dto);
    List<SubCategoriaDto> listar();
    Optional<SubCategoria> buscarPorId(long id);
    void eliminar(SubCategoria subCategoria);
}
