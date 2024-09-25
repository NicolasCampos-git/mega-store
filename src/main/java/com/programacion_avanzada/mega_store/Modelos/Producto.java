package com.programacion_avanzada.mega_store.Modelos;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "productos")
public class Producto {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name = "nombre")
    private String nombre;

    @NotBlank
    @Column(name = "descripcion")
    private String descripcion;

    @NotBlank
    @Column(name = "tamano")
    private String tamano;

    @NotBlank
    @Column(name = "color")
    private String color;

    @NotNull
    @Column(name = "precio_unitario")
    private double precioUnitario;

    @NotNull
    @Column(name = "stock")
    private int stock;

    @NotNull
    @Column(name = "umbral_bajo_stock")
    private int umbralBajoStock;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @NonNull
    @Column(name = "esta_activo")
    private boolean estaActivo;

}
