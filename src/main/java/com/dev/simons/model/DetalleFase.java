package com.dev.simons.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class DetalleFase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Integer numeroFase;
    private String actividad;
    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Responsable responsable;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer numeroDias;
    private String porcentajeAvance;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "fase_proyecto_id")
    private FaseProyecto faseProyecto;
}
