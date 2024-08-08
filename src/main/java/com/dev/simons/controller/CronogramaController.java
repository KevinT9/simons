package com.dev.simons.controller;

import com.dev.simons.model.*;
import com.dev.simons.repository.IndicadoresProyectoRepository;
import com.dev.simons.repository.ProyectoRepository;
import com.dev.simons.repository.ResponsableRepository;
import com.dev.simons.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class CronogramaController {

    private final UsuarioService usuarioService;
    private final ProyectoRepository proyectoRepository;
    private final ResponsableRepository responsableRepository;
    private final IndicadoresProyectoRepository indicadoresProyectoRepository;

    public CronogramaController(UsuarioService usuarioService, ProyectoRepository proyectoRepository,
                                ResponsableRepository responsableRepository, IndicadoresProyectoRepository indicadoresProyectoRepository) {
        this.usuarioService = usuarioService;
        this.proyectoRepository = proyectoRepository;
        this.responsableRepository = responsableRepository;
        this.indicadoresProyectoRepository = indicadoresProyectoRepository;
    }


    @GetMapping("/seleccionarProyecto")
    public String seleccionarProyecto(Model model) {
        model.addAttribute("usuario", usuarioService.getAuthenticatedUser());
        List<Proyecto> proyectos = proyectoRepository.findAll();
        model.addAttribute("proyectos", proyectos);

        return "seleccionarProyecto";
    }

    @GetMapping("/cronograma/{id}")
    public String cronograma(Model model, @PathVariable Long id) {
        model.addAttribute("usuario", usuarioService.getAuthenticatedUser());

        Optional<Proyecto> proyecto = proyectoRepository.findById(id);

        if (proyecto.isEmpty()) {
            return "redirect:/seleccionarProyecto";
        }

        Proyecto proyecto1 = proyecto.get();

        model.addAttribute("proyecto", proyecto1);

        // Validar que getFases() no sea null
        if (proyecto1.getFases().size() != 4) {
            return "redirect:/seleccionarProyecto";
        }
        List<DetalleFase> detalleFases1 = proyecto1.getFases().get(0).getDetallesFase();
        model.addAttribute("detallesFase1", detalleFases1);
        List<DetalleFase> detalleFases2 = proyecto1.getFases().get(1).getDetallesFase();
        model.addAttribute("detallesFase2", detalleFases2);
        List<DetalleFase> detalleFases3 = proyecto1.getFases().get(2).getDetallesFase();
        model.addAttribute("detallesFase3", detalleFases3);
        List<DetalleFase> detalleFases4 = proyecto1.getFases().get(3).getDetallesFase();
        model.addAttribute("detallesFase4", detalleFases4);

        model.addAttribute("responsables", responsableRepository.findAll());

        model.addAttribute("idFase1", proyecto1.getFases().get(0).getId());
        model.addAttribute("idFase2", proyecto1.getFases().get(1).getId());
        model.addAttribute("idFase3", proyecto1.getFases().get(2).getId());
        model.addAttribute("idFase4", proyecto1.getFases().get(3).getId());

        IndicadoresProyecto indicadoresProyecto = indicadoresProyectoRepository.findByProyecto(proyecto1);
        model.addAttribute("indicadoresProyecto", indicadoresProyecto);

        // Enviar la cantidad de fechas excluidas por proyecto
        model.addAttribute("cantidadFechasExcluidas", proyecto1.getFechasExcluidas().size());

        // Enviar duración de proyecto en días quitando sabados y domingos
        LocalDate fechaInicio = proyecto1.getFechaInicio();
        LocalDate fechaFin = proyecto1.getFechaFin();
        List<ExcluirFecha> fechasExcluidas = proyecto1.getFechasExcluidas();
        List<LocalDate> fechasExcluidasList = fechasExcluidas.stream().map(ExcluirFecha::getFecha).toList();
        long duracionDias = calcularDuracionEnDias(fechaInicio, fechaFin, fechasExcluidas);
        long duracionMeses = calcularDuracionEnMeses(fechaInicio, fechaFin, fechasExcluidas);

        model.addAttribute("duracionDias", duracionDias);
        model.addAttribute("duracionMeses", duracionMeses);


        return "cronograma";
    }


    public long calcularDuracionEnDias(LocalDate fechaInicio, LocalDate fechaFin, List<ExcluirFecha> fechasExcluidas) {
        long totalDias = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1; // +1 para incluir el último día

        for (ExcluirFecha excluirFecha : fechasExcluidas) {
            LocalDate fechaExcluida = excluirFecha.getFecha();
            if ((fechaExcluida.isAfter(fechaInicio) || fechaExcluida.equals(fechaInicio)) &&
                    (fechaExcluida.isBefore(fechaFin) || fechaExcluida.equals(fechaFin))) {
                totalDias--;
            }
        }

        return totalDias;
    }

    public long calcularDuracionEnMeses(LocalDate fechaInicio, LocalDate fechaFin, List<ExcluirFecha> fechasExcluidas) {
        long totalDias = calcularDuracionEnDias(fechaInicio, fechaFin, fechasExcluidas);
        return totalDias / 30;
    }

}
