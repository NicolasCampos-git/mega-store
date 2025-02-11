package com.programacion_avanzada.mega_store.DTOs.DireccionDtos;



import lombok.Data;

@Data
public class DireccionEnvioDto { 



    private String provincia;


    private String ciudad;


    private String calle;

    private String altura;

    private String codigoPostal;

    private String descripcionDireccionEnvio;

    private boolean esPrincipal;
    
}
