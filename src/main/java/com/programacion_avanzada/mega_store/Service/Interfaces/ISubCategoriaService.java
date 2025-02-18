package com.programacion_avanzada.mega_store.Service.Interfaces;

import java.util.List;

import com.programacion_avanzada.mega_store.DTOs.SubcategoriaDtos.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;

public interface ISubCategoriaService {
    SubCategoria registrarSubCategoria(RegistrarSubCategoriaDto dto);
    List<SubCategoria> listar();
    SubCategoria buscarPorId(long id);
    void eliminar(long id);
    void reactivar(long id);
    SubCategoria actualizar(long id, RegistrarSubCategoriaDto dto);
}
