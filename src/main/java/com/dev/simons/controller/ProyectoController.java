package com.dev.simons.controller;

import com.dev.simons.repository.ProyectoRepository;
import com.dev.simons.repository.ResponsableRepository;
import com.dev.simons.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@Slf4j
public class ProyectoController {

    private final UsuarioService usuarioService;
    private final ProyectoRepository proyectoRepository;
    private final ResponsableRepository responsableRepository;

    public ProyectoController(UsuarioService usuarioService, ProyectoRepository proyectoRepository,
                              ResponsableRepository responsableRepository) {
        this.usuarioService = usuarioService;
        this.proyectoRepository = proyectoRepository;
        this.responsableRepository = responsableRepository;
    }

    @GetMapping("/proyecto")
    public String proyecto(Model model) {
        model.addAttribute("usuario", usuarioService.getAuthenticatedUser());
        model.addAttribute("proyectos", proyectoRepository.findAll());
        model.addAttribute("responsables", responsableRepository.findAll());
        return "proyecto";
    }


}
