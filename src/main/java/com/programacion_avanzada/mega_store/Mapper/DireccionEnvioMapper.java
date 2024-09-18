package com.programacion_avanzada.mega_store.Mapper;

import com.programacion_avanzada.mega_store.DTOs.DireccionEnvioDto;
import com.programacion_avanzada.mega_store.Modelos.DireccionEnvio;



public class DireccionEnvioMapper {

    
    public static DireccionEnvioDto toDto(DireccionEnvio direccionEnvio){
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setProvincia(direccionEnvio.getProvincia());
        dto.setCiudad(direccionEnvio.getCiudad());
        dto.setCalle(direccionEnvio.getCalle());
        dto.setAltura(direccionEnvio.getAltura());
        dto.setCodigoPostal(direccionEnvio.getCodigoPostal());
        dto.setDescripcionDireccionEnvio(direccionEnvio.getDescripcionDireccionEnvio());


        return dto;
    }

    public static DireccionEnvio toEntity(DireccionEnvioDto dto){
        DireccionEnvio direccionEnvio = new DireccionEnvio();

        direccionEnvio.setProvincia(dto.getProvincia());
        direccionEnvio.setCiudad(dto.getCiudad());
        direccionEnvio.setCalle(dto.getCalle());
        direccionEnvio.setAltura(dto.getAltura());
        direccionEnvio.setCodigoPostal(dto.getCodigoPostal());
        direccionEnvio.setDescripcionDireccionEnvio(dto.getDescripcionDireccionEnvio());


        return direccionEnvio;
    }


}
