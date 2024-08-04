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
public class ExcluirFechasController {

    private final UsuarioService usuarioService;

    public ExcluirFechasController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/excluir-fechas")
    public String calendario(Model model) {
        model.addAttribute("usuario", usuarioService.getAuthenticatedUser());
        return "excluir-fechas";
    }
}
