package com.dev.simons.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ResponsableDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String sexo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaNacimiento;
    private String dni;
    private String cargo;
}
