package com.programacion_avanzada.mega_store.DTOs;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DireccionEnvioDto { 
    //Atributos necesarios para agregar una direccion.
    @NotNull
    @NotBlank
    private String provincia;

    @NotNull
    @NotBlank
    private String ciudad;

    @NotNull
    @NotBlank
    private String calle;

    @NotNull
    @NotBlank
    @Digits(integer = 6, fraction = 1, message = "El teléfono debe ser un número de hasta 6 dígitos.")
    private String altura;

    @Pattern(regexp = "^[A-Z]\\d{4}[A-Z]{3}$", message = "El código postal debe seguir el formato CPA válido.")
    private String codigoPostal;

    private String descripcionDireccionEnvio;

    @NotNull
    private boolean estaActivo;
    
}
