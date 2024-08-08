package com.dev.simons.model;

import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class IndicadoresProyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int totalActividades;
    private int totalActividadesCompletadas;
    private int totalActividadesAtrasados;
    private int totalActividadesSinEmpezar;
    private int totalActividadesEnProceso;
    private int totalPorcentajeAvance;

    @OneToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;

}
