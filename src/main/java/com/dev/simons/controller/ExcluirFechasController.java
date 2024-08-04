package com.dev.simons.controller;

import com.dev.simons.repository.ExcluirFechaRepository;
import com.dev.simons.repository.ProyectoRepository;
import com.dev.simons.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@Slf4j
public class ExcluirFechasController {

    private final UsuarioService usuarioService;
    private final ProyectoRepository proyectoRepository;

    public ExcluirFechasController(UsuarioService usuarioService, ProyectoRepository proyectoRepository) {
        this.usuarioService = usuarioService;
        this.proyectoRepository = proyectoRepository;
    }

    @GetMapping("/excluir-fechas")
    public String calendario(Model model) {
        model.addAttribute("usuario", usuarioService.getAuthenticatedUser());
        model.addAttribute("proyectos", proyectoRepository.findAll());
        return "excluir-fechas";
    }


}
