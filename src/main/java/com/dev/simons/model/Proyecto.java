package com.dev.simons.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;
    @Column(columnDefinition = "TEXT")
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
    private LocalDate fechaInicio;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaFin;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaCreacion;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaActualizacion;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<FaseProyecto> fases = new ArrayList<>();

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ExcluirFecha> fechasExcluidas = new ArrayList<>();

}
