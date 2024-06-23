package com.dev.simons.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;
    private String descripcion;
    private String estado;
    private String prioridad;
    private String distrito;
    private String direccion;
    @ManyToOne
    @JoinColumn(name = "responsable_id", referencedColumnName = "id", nullable = false)
    private Responsable responsable;
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    private Cliente cliente;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaInicio;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaFin;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaCreacion;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaActualizacion;

}
