package com.programacion_avanzada.mega_store.Service;

import java.util.List;
import java.util.Optional;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.Categoria;

public interface ICategoriaService {
    CategoriaDto registrarCategoria(CategoriaDto dto);
    List<CategoriaDto> listar();
    Optional<Categoria> buscarPorId(long id);
    void eliminar(Categoria categoria);
}
