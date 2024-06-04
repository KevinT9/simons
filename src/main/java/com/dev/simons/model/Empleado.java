package com.dev.simons.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Table
@Entity
@Getter
@Setter
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String cargo;
    private String sexo;
    private Date fechaInicio;

}
