package com.programacion_avanzada.mega_store.DTOs;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrarSubCategoriaDto {
    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "^[^\\d]*$")
    private String nombre;

    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "^[^\\d]*$")
    private String descripcion;

    @NonNull
    private long categoriaId;
}
