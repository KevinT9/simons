package com.dev.simons.rest;

import com.dev.simons.model.ExcluirFecha;
import com.dev.simons.model.Proyecto;
import com.dev.simons.repository.ExcluirFechaRepository;
import com.dev.simons.repository.ProyectoRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/excluir-fecha")
@Slf4j
public class ExcluirFechaRestController {

    private final ExcluirFechaRepository excluirFechaRepository;
    private final ProyectoRepository proyectoRepository;

    public ExcluirFechaRestController(ExcluirFechaRepository excluirFechaRepository, ProyectoRepository proyectoRepository) {
        this.excluirFechaRepository = excluirFechaRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @PostMapping("/")
    ResponseEntity<?> excluirFecha(@RequestBody ExcluirFechaDTO excluirFechaDTO) {
        log.info("Fecha excluida");

        LocalDate fecha;

        // Validar la fecha
        try {
            fecha = LocalDate.parse(excluirFechaDTO.getFecha());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fecha inválida");
        }

        String motivo = excluirFechaDTO.getMotivo();
        Long proyectoID = excluirFechaDTO.getProyectoID();

        // Validar si existe el proyecto
        Optional<Proyecto> proyectoOptional = proyectoRepository.findById(proyectoID);
        if (proyectoOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Proyecto no encontrado");
        }

        // Crear la fecha excluida
        ExcluirFecha excluirFecha = new ExcluirFecha();
        excluirFecha.setFecha(fecha);
        excluirFecha.setMotivo(motivo);
        excluirFecha.setProyecto(proyectoOptional.get());
        excluirFechaRepository.save(excluirFecha);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    ResponseEntity<ExcluirFecha> getExcluirFecha(@PathVariable Long id) {
        return ResponseEntity.ok(excluirFechaRepository.findById(id).orElse(null));
    }

    @GetMapping("/")
    ResponseEntity<List<ExcluirFecha>> getExcluirFechas() {
        return ResponseEntity.ok(excluirFechaRepository.findAll());
    }

    @GetMapping("/proyecto/{id}")
    ResponseEntity<List<ExcluirFecha>> getExcluirFechasByProyecto(@PathVariable Long id) {
        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
        return ResponseEntity.ok(excluirFechaRepository.findAllByProyecto(proyecto));
    }


    @Data
    static class ExcluirFechaDTO {
        private String fecha;
        private String motivo;
        private Long proyectoID;
    }


}
