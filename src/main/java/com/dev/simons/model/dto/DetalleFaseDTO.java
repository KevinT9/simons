package com.dev.simons.model.dto;

import lombok.Data;

@Data
public class DetalleFaseDTO {
    private Long id;
    private Integer numeroFase;
    private String actividad;
    private Long responsableId;
    private String fechaInicio;
    private String fechaFin;
    private Integer numeroDias;
    private String porcentajeAvance;
    private String estado;
    private Long faseProyectoId;
}
