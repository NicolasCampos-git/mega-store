package com.programacion_avanzada.mega_store.Service.Interfaces;

public interface ISenderService {
    void enviarCorreo(String email);
    void notificarBajoStock(long idProducto);
    
}
