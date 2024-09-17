package com.programacion_avanzada.mega_store.Modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="nombre")
    private String nombre;

    @Column(name ="apellido")
    private String apellido;

    @Column(name ="telefono")
    private String telefono;

    //Datos para validar al usuario.
    @Column(name ="mail")
    private String mail;
    @Column(name ="contrasena")
    private String contrasena;

    
    @Column(name ="rol")
    private String rol; //Rol que va a tener el usuario. lo dejamos como string para simplificar.

    //Falta agregar direccion.

}
