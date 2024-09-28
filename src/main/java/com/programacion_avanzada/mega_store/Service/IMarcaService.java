package com.programacion_avanzada.mega_store.Service;

import java.util.List;


import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.Modelos.Marca;



public interface IMarcaService {
    MarcaDto registrarMarca(MarcaDto dto);
    List<MarcaDto> listar();
    Marca buscarPorId(long id);
    void eliminar(long id);
}
