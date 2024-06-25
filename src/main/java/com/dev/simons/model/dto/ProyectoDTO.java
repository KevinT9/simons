package com.dev.simons.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ProyectoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    private String prioridad;
    private String distrito;
    private String direccion;
    private Long responsableId;
    private ClienteDTO cliente;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaInicio;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaFin;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaCreacion;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaActualizacion;
}
