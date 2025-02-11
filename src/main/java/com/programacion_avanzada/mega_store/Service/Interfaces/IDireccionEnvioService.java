package com.programacion_avanzada.mega_store.Service.Interfaces;

import java.util.List;

import com.programacion_avanzada.mega_store.DTOs.DireccionDtos.DireccionEnvioDto;
import com.programacion_avanzada.mega_store.Modelos.DireccionEnvio;



public interface IDireccionEnvioService {
    
    DireccionEnvioDto agregaDireccionEnvio(long id, DireccionEnvioDto dto);

    DireccionEnvio buscarDireccionEnvio(long id);

    DireccionEnvio actualizarDireccionEnvio(long id, DireccionEnvioDto dto);

    DireccionEnvio reactivarDireccionEnvio(long id);

    List<DireccionEnvio> listarDireccionEnvioPorUsuario(long usuarioId);

    List<DireccionEnvio> listar();

    void eliminarDireccionEnvio(long id);
    
}
