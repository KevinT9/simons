package com.dev.simons.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String role;

    @OneToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;



}
