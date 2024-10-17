package com.programacion_avanzada.mega_store.Service;

import java.util.List;

import com.programacion_avanzada.mega_store.DTOs.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;

public interface ISubCategoriaService {
    RegistrarSubCategoriaDto registrarSubCategoria(RegistrarSubCategoriaDto dto);
    List<SubCategoriaDto> listar();
    SubCategoria buscarPorId(long id);
    void eliminar(long id);
    void reactivar(long id);
    SubCategoriaDto actualizar(long id, SubCategoriaDto dto);
}
