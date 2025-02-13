package com.programacion_avanzada.mega_store.Service.Interfaces;

import java.util.List;

import com.programacion_avanzada.mega_store.DTOs.ReclamoDtos.CambioEstadoReclamoDto;
import com.programacion_avanzada.mega_store.DTOs.ReclamoDtos.RegistrarReclamoDto;
import com.programacion_avanzada.mega_store.Modelos.Reclamo;

public interface IReclamoService {
    Reclamo registrarReclamo(RegistrarReclamoDto dto);
    Reclamo buscarPorId(long id);
    List<Reclamo> listarReclamos();
    Reclamo actualizarReclamo(CambioEstadoReclamoDto dto);
    List<Reclamo> listarReclamoPorUsuario(long id);

    Reclamo revisarReclamo(long idReclamo);

    Reclamo aprobarReclamo(long idReclamo);

    Reclamo rechazarReclamo(long idReclamo);

    Reclamo resolverReclamo(long idReclamo);

    void eliminarReclamo(long id);
    void reactivarReclamo(long id);
}
