package com.programacion_avanzada.mega_store.Modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_compra_id")
    private OrdenCompra ordenCompra;


    @ManyToOne
    private Producto producto;

    @Column
    private Integer cantidad;

    @Column
    private Double subtotal;
}
