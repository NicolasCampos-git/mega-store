package com.programacion_avanzada.mega_store.Service;

import com.programacion_avanzada.mega_store.DTOs.DireccionEnvioDto;
import com.programacion_avanzada.mega_store.Modelos.DireccionEnvio;



public interface IDireccionEnvioService {
    
    DireccionEnvioDto agregaDireccionEnvio(long id, DireccionEnvioDto dto);

    DireccionEnvio buscarDireccionEnvio(long id);

    DireccionEnvio actualizarDireccionEnvio(long id, DireccionEnvioDto dto);

    void eliminarDireccionEnvio(long id);

    DireccionEnvio reactivarDireccionEnvio(long id);
    
}
