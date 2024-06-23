package com.dev.simons.controller;

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

    public ProyectoController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/proyecto")
    public String proyecto(Model model) {
        model.addAttribute("usuario", usuarioService.getAuthenticatedUser());
        return "proyecto";
    }
}
