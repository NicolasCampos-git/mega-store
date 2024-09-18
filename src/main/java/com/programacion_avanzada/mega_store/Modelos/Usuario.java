package com.programacion_avanzada.mega_store.Modelos;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @Column(name ="nombre")
    private String nombre;

    @Column(name ="apellido")
    private String apellido;

    //Datos para validar al usuario.
    @Column(name ="email")
    private String email;
    @Column(name ="contrasena")
    private String contrasena;

    //Relacion 1:N
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true) //De esta forma salteamos tener que tener la clase de la tabla intermedia y se puede trabajar en el PUT.
    private List<DireccionEnvio> direcciones;

    
    @Column(name ="rol")
    private String rol; //Rol que va a tener el usuario. lo dejamos como string para simplificar.

    //Creo un nuevo metodo para settear 1 direccion.
    public void setDireccion(DireccionEnvio direccion) {
        direcciones.add(direccion);
        direccion.setUsuario(this);
    }

    

}
