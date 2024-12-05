package com.programacion_avanzada.mega_store.Modelos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name ="nombre")
    private String nombre;

    @NotBlank
    @Column(name ="apellido")
    private String apellido;

    @NotBlank
    //Datos para validar al usuario.
    @Column(name ="email")
    private String email;

    @NotBlank
    @Column(name ="contrasena")
    private String contrasena;

    @NotBlank
    @Column(name = "telefono")
    private String telefono;

    //Relacion 1:N
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true) //De esta forma salteamos tener que tener la clase de la tabla intermedia y se puede trabajar en el PUT.
    @JsonManagedReference
    private List<DireccionEnvio> direcciones;

    
    @Column(name = "esta_activo")
    private boolean estaActivo;
    
    @Column(name ="rol")
    private String rol; //Rol que va a tener el usuario. lo dejamos como string para simplificar.


    

}
