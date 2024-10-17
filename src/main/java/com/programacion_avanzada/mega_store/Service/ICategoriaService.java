package com.programacion_avanzada.mega_store.Service;

import java.util.List;


import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarCategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.Categoria;

public interface ICategoriaService {
    RegistrarCategoriaDto registrarCategoria(RegistrarCategoriaDto dto);
    List<CategoriaDto> listar();
    Categoria buscarPorId(long id);
    void eliminar(long id);
    CategoriaDto actualizar(long id, CategoriaDto dto);
    void reactivar(long id);
}
