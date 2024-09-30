package com.programacion_avanzada.mega_store.DTOs;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DireccionEnvioDto { 
        //Atributos necesarios para agregar una direccion.

    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String provincia;


    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String ciudad;


    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String calle;


    @NotBlank
    @Digits(integer = 6, fraction = 1)
    private String altura;

    @Size(min = 2)
    @Pattern(regexp = "^[0-9]{2,4}$")
    private String codigoPostal;

    private String descripcionDireccionEnvio;

    @NotNull
    private boolean esPrincipal;
    
}
