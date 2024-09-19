package com.programacion_avanzada.mega_store.Service;

import com.programacion_avanzada.mega_store.DTOs.DireccionEnvioDto;


public interface IDireccionEnvioService {
    
    public DireccionEnvioDto agregaDireccionEnvio(long id, DireccionEnvioDto dto);
}
