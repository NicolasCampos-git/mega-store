package com.programacion_avanzada.mega_store.DTOs;



import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Data;

import java.util.Set;

//Este DTO tiene estos atributos para poder hacer el registro del usuario.
@Data
public class RegistroUsuarioDto {

    
    private String nombre;

    
    private String apellido;

    private String telefono;

    private String email;

  
    private String contrasena;

    private String contrasenaRepetida;

}
