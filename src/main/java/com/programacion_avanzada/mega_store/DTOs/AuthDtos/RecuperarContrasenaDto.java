package com.programacion_avanzada.mega_store.DTOs.AuthDtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RecuperarContrasenaDto {

    @NotEmpty
    private String email;
}
