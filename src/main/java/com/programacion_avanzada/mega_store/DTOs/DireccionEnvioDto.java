package com.programacion_avanzada.mega_store.DTOs;

import lombok.Data;

@Data
public class DireccionEnvioDto { 
    //Atributos necesarios para agregar una direccion.
    private String provincia;
    private String ciudad;
    private String calle;
    private String altura;
    private String codigoPostal;
    private String descripcionDireccionEnvio;
    
}
