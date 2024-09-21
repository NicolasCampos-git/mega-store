package com.programacion_avanzada.mega_store.Modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //Genera los getters de la clase.
@Setter //Genera los setters de la clase.
@Builder //
@AllArgsConstructor
@NoArgsConstructor //Genera un constructor son argimentos.
@Entity //Especifica que va a ser una entidad de la db.
@Table(name = "direcciones_envio") //Le da el nombre a la tabla de la db
public class DireccionEnvio {
    @Id //Espefica que es el id.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //En java se una long en el id.

    @Column(name = "provincia") //Le da el nombre a la columna
    private String provincia;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "calle")
    private String calle;

    @Column(name = "altura")
    private String altura;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    

    @Column(name = "descripcion_direccion_envio")
    private String descripcionDireccionEnvio; 

    @ManyToOne //Relacion N:1
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "es_principal")
    private boolean esPrincipal;

}
