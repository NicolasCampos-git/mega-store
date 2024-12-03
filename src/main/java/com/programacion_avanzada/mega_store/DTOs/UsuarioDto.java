package com.programacion_avanzada.mega_store.DTOs;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

//Este DTO es en caso de queter motrar datos del usuario.
@Data
public class UsuarioDto {


    private String nombre;

    
    private String apellido;

    
    private String telefono;

   
    private String email;

    private String rol;


}
