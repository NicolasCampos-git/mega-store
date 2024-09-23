package com.programacion_avanzada.mega_store.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CategoriaDto {
    
    @NotBlank
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\u00f1\\u00d1 ]+$")
    private String nombre;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\u00f1\\u00d1 ]+$")
    private String descripcion;

}
