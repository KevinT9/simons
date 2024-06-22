package com.dev.simons.controller;

import com.dev.simons.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/inicio")
    public String index(Model model) {
        model.addAttribute("usuario", usuarioService.getAuthenticatedUser());
        return "index";
    }

    @GetMapping("/")
    public String index2() {
        return "index";
    }

}
