package com.programacion_avanzada.mega_store.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SubCategoriaDto {

    @NotBlank
    @Pattern(regexp = "^[^\\d]*$")
    private String nombre;

    @NotBlank
    @Pattern(regexp = "^[^\\d]*$")
    private String descripcion;

    
}
