package com.programacion_avanzada.mega_store.Service.Interfaces;

import java.util.List;

import com.programacion_avanzada.mega_store.DTOs.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;

public interface ISubCategoriaService {
    RegistrarSubCategoriaDto registrarSubCategoria(RegistrarSubCategoriaDto dto);
    List<SubCategoria> listar();
    SubCategoria buscarPorId(long id);
    void eliminar(long id);
    void reactivar(long id);
    SubCategoriaDTO actualizar(long id, RegistrarSubCategoriaDto dto);
}
