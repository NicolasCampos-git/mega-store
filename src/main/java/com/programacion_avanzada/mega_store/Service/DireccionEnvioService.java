package com.programacion_avanzada.mega_store.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.DireccionEnvioDto;
import com.programacion_avanzada.mega_store.Mapper.DireccionEnvioMapper;
import com.programacion_avanzada.mega_store.Modelos.DireccionEnvio;
import com.programacion_avanzada.mega_store.Modelos.Usuario;
import com.programacion_avanzada.mega_store.Repository.DireccionEnvioRepository;
import com.programacion_avanzada.mega_store.Repository.UsuarioRepository;

@Service
public class DireccionEnvioService implements IDireccionEnvioService {
    @Autowired
    DireccionEnvioRepository direccionEnvioRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public DireccionEnvioDto agregaDireccionEnvio(long usuarioId, DireccionEnvioDto dto){
        DireccionEnvio direccionEnvio = DireccionEnvioMapper.toEntity(dto);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        direccionEnvio.setUsuario(usuario);
        



        return DireccionEnvioMapper.toDto(direccionEnvioRepository.save(direccionEnvio));
    }


}
