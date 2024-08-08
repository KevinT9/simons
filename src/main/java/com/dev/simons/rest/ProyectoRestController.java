package com.dev.simons.rest;

import com.dev.simons.model.*;
import com.dev.simons.model.dto.DetalleFaseDTO;
import com.dev.simons.model.dto.ProyectoDTO;
import com.dev.simons.repository.*;
import com.dev.simons.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/proyectos")
@Slf4j
public class ProyectoRestController {

    private final ProyectoRepository proyectoRepository;
    private final ResponsableRepository responsableRepository;
    private final ClienteService clienteService;
    private final FaseProyectoRepository faseProyectoRepository;
    private final DetalleFaseRepository detalleFaseRepository;
    private final IndicadoresProyectoRepository indicadoresProyectoRepository;

    public ProyectoRestController(ProyectoRepository proyectoRepository, ResponsableRepository responsableRepository,
                                  ClienteService clienteService, FaseProyectoRepository faseProyectoRepository,
                                  DetalleFaseRepository detalleFaseRepository, IndicadoresProyectoRepository indicadoresProyectoRepository) {
        this.proyectoRepository = proyectoRepository;
        this.responsableRepository = responsableRepository;
        this.clienteService = clienteService;
        this.faseProyectoRepository = faseProyectoRepository;
        this.detalleFaseRepository = detalleFaseRepository;
        this.indicadoresProyectoRepository = indicadoresProyectoRepository;
    }

    @PostMapping("/")
    @Transactional
    public ResponseEntity<?> guardarProyecto(@RequestBody ProyectoDTO proyectoDTO) {
        log.info("ProyectoDTO recibido: {}", proyectoDTO);

        // Obtener el cliente, creándolo si no existe
        Cliente cliente = clienteService.obtenerOcrearCliente(proyectoDTO.getCliente());

        // Obtener el responsable
        Responsable responsable = responsableRepository.findById(proyectoDTO.getResponsableId())
                .orElseThrow(() -> new EntityNotFoundException("Responsable no encontrado"));

        // Crear el proyecto
        Proyecto proyecto = Proyecto.builder()
                .nombre(proyectoDTO.getNombre())
                .descripcion(proyectoDTO.getDescripcion())
                .estado("EN PROCESO")
                .prioridad(proyectoDTO.getPrioridad())
                .distrito(proyectoDTO.getDistrito())
                .direccion(proyectoDTO.getDireccion())
                .responsable(responsable)
                .cliente(cliente)
                .fechaInicio(proyectoDTO.getFechaInicio())
                .fechaFin(proyectoDTO.getFechaFin())
                .fechaCreacion(LocalDate.now())
                .fechaActualizacion(null)
                .build();

        proyecto = proyectoRepository.save(proyecto);

        // Crear las 4 fases de proyecto
        // Fase 1
        FaseProyecto fase1 = FaseProyecto.builder()
                .numero("1")
                .proyecto(proyecto)
                .build();
        // Fase 2
        FaseProyecto fase2 = FaseProyecto.builder()
                .numero("2")
                .proyecto(proyecto)
                .build();
        // Fase 3
        FaseProyecto fase3 = FaseProyecto.builder()
                .numero("3")
                .proyecto(proyecto)
                .build();
        // Fase 4
        FaseProyecto fase4 = FaseProyecto.builder()
                .numero("4")
                .proyecto(proyecto)
                .build();

        faseProyectoRepository.save(fase1);
        faseProyectoRepository.save(fase2);
        faseProyectoRepository.save(fase3);
        faseProyectoRepository.save(fase4);

        proyecto = proyectoRepository.findById(proyecto.getId()).get();

        // Crear los indicadores del proyecto
        IndicadoresProyecto indicadoresProyecto = IndicadoresProyecto.builder()
                .totalActividades(0)
                .totalActividadesCompletadas(0)
                .totalActividadesAtrasados(0)
                .totalActividadesSinEmpezar(0)
                .totalActividadesEnProceso(0)
                .totalPorcentajeAvance(0)
                .proyecto(proyecto)
                .build();

        indicadoresProyectoRepository.save(indicadoresProyecto);

        log.info("Proyecto guardado: {}", proyecto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<?> eliminarProyecto(@PathVariable Long id) {
        log.info("Proyecto eliminado: {}", proyectoRepository.findById(id).orElseThrow());

        // Buscar indicadores del proyecto y eliminarlos
        IndicadoresProyecto indicadoresProyecto = indicadoresProyectoRepository.findByProyecto(proyectoRepository.findById(id).get());
        indicadoresProyectoRepository.delete(indicadoresProyecto);


        proyectoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fase/{faseId}/detalle-fase/guardar")
    @Transactional
    ResponseEntity<?> guardarDetalleFase(@PathVariable Long faseId, @RequestBody DetalleFaseDTO detalleFaseDTO) {
        log.info("Guardando DetalleFaseDTO recibido: {}", detalleFaseDTO);
        // Obtener la fase de proyecto
        FaseProyecto faseProyecto = faseProyectoRepository.findById(faseId)
                .orElseThrow(() -> new EntityNotFoundException("Fase de proyecto no encontrada"));
        // Obtener el responsable
        Responsable responsable = responsableRepository.findById(detalleFaseDTO.getResponsableId())
                .orElseThrow(() -> new EntityNotFoundException("Responsable no encontrado"));
        // Crear el detalle de fase
        DetalleFase detalleFase = DetalleFase.builder()
                .numeroFase(detalleFaseDTO.getNumeroFase())
                .actividad(detalleFaseDTO.getActividad())
                .responsable(responsable)
                .fechaInicio(LocalDate.parse(detalleFaseDTO.getFechaInicio()))
                .fechaFin(LocalDate.parse(detalleFaseDTO.getFechaFin()))
                .numeroDias(detalleFaseDTO.getNumeroDias())
                .porcentajeAvance(detalleFaseDTO.getPorcentajeAvance())
                .estado(detalleFaseDTO.getEstado())
                .faseProyecto(faseProyecto)
                .build();
        detalleFaseRepository.save(detalleFase);

        // Buscar los indicadores
        IndicadoresProyecto indicadoresProyecto = indicadoresProyectoRepository.findByProyecto(faseProyecto.getProyecto());

        // Actualizar los indicadores del proyecto
        indicadoresProyecto.setTotalActividades(indicadoresProyecto.getTotalActividades() + 1);
        if (detalleFase.getEstado().equalsIgnoreCase("COMPLETADO")) {
            indicadoresProyecto.setTotalActividadesCompletadas(indicadoresProyecto.getTotalActividadesCompletadas() + 1);
        } else if (detalleFase.getEstado().equalsIgnoreCase("ATRASADO")) {
            indicadoresProyecto.setTotalActividadesAtrasados(indicadoresProyecto.getTotalActividadesAtrasados() + 1);
        } else if (detalleFase.getEstado().equalsIgnoreCase("SIN EMPEZAR")) {
            indicadoresProyecto.setTotalActividadesSinEmpezar(indicadoresProyecto.getTotalActividadesSinEmpezar() + 1);
        } else if (detalleFase.getEstado().equalsIgnoreCase("EN PROCESO")) {
            indicadoresProyecto.setTotalActividadesEnProceso(indicadoresProyecto.getTotalActividadesEnProceso() + 1);
        }

        indicadoresProyectoRepository.save(indicadoresProyecto);


        log.info("Detalle de fase guardado: {}", detalleFase);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fase/{faseId}/detalle-fase/actualizar")
    ResponseEntity<?> actualizarDetalleFase(@RequestBody DetalleFaseDTO detalleFaseDTO, @PathVariable String faseId) {
        log.info("DetalleFaseDTO recibido: {}", detalleFaseDTO);
        // Obtener el detalle de fase
        DetalleFase detalleFase = detalleFaseRepository.findById(detalleFaseDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Detalle de fase no encontrado"));
        // Actualizar el detalle de fase
        detalleFase.setActividad(detalleFaseDTO.getActividad());
        detalleFase.setFechaInicio(LocalDate.parse(detalleFaseDTO.getFechaInicio()));
        detalleFase.setFechaFin(LocalDate.parse(detalleFaseDTO.getFechaFin()));
        detalleFase.setNumeroDias(detalleFaseDTO.getNumeroDias());
        detalleFase.setPorcentajeAvance(detalleFaseDTO.getPorcentajeAvance());
        detalleFase.setEstado(detalleFaseDTO.getEstado());
        detalleFaseRepository.save(detalleFase);
        log.info("Detalle de fase actualizado: {}", detalleFase);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fase/{faseId}/detalle-fase/eliminar/{idDetalleFase}")
    ResponseEntity<?> eliminarDetalleFase(@PathVariable Long faseId, @PathVariable Long idDetalleFase) {
        log.info("Eliminando DetalleFaseDTO recibido: {}", idDetalleFase);
        // Obtener el detalle de fase
        DetalleFase detalleFase = detalleFaseRepository.findById(idDetalleFase)
                .orElseThrow(() -> new EntityNotFoundException("Detalle de fase no encontrado"));
        // Eliminar el detalle de fase
        detalleFaseRepository.delete(detalleFase);
        log.info("Detalle de fase eliminado: {}", detalleFase);
        return ResponseEntity.ok().build();
    }
}
