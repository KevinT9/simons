package com.dev.simons.rest;

import com.dev.simons.model.Cliente;
import com.dev.simons.model.Proyecto;
import com.dev.simons.model.Responsable;
import com.dev.simons.model.dto.ProyectoDTO;
import com.dev.simons.repository.ProyectoRepository;
import com.dev.simons.repository.ResponsableRepository;
import com.dev.simons.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/proyectos")
@Slf4j
public class ProyectoRestController {

    private final ProyectoRepository proyectoRepository;
    private final ResponsableRepository responsableRepository;
    private final ClienteService clienteService;

    public ProyectoRestController(ProyectoRepository proyectoRepository, ResponsableRepository responsableRepository, ClienteService clienteService) {
        this.proyectoRepository = proyectoRepository;
        this.responsableRepository = responsableRepository;
        this.clienteService = clienteService;
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

        proyectoRepository.save(proyecto);

        log.info("Proyecto guardado: {}", proyecto);
        return ResponseEntity.ok().build();
    }
}
