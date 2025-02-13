package com.programacion_avanzada.mega_store.Modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
public class OrdenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemOrden> items;


    @Column
    private Double total;

    /*
     * Atirubutos de la con los datos de los estados de la orden
    */

    @ManyToOne
    private Estado estado;

    @Column
    private LocalDateTime fecha; //fecha de creacion

    @Column
    private LocalDateTime fechaPago;

    @Column
    private LocalDateTime fechaCancelacion;

    @Column
    private LocalDateTime fechaEnvio;

    @Column
    private LocalDateTime fechaEntrega;

    @Column
    private boolean estaActiva = true;

    
    
}
