package com.programacion_avanzada.mega_store.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MarcaDto {
    @NotBlank
    @Pattern(regexp = "^[^\\d]*$")
    private String nombre;

    @NotBlank
    @Pattern(regexp = "^[^\\d]*$")
    private String descripcion;
}
