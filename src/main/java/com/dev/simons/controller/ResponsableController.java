package com.dev.simons.controller;

import com.dev.simons.model.Responsable;
import com.dev.simons.model.dto.ResponsableDTO;
import com.dev.simons.repository.ResponsableRepository;
import com.dev.simons.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
@Slf4j
public class ResponsableController {

    private final UsuarioService usuarioService;
    private final ResponsableRepository responsableRepository;

    public ResponsableController(UsuarioService usuarioService, ResponsableRepository responsableRepository) {
        this.usuarioService = usuarioService;
        this.responsableRepository = responsableRepository;
    }

    @GetMapping("/responsable")
    public String responsable(Model model) {
        model.addAttribute("usuario", usuarioService.getAuthenticatedUser());
        model.addAttribute("responsables", responsableRepository.findAll());
        return "responsable";
    }

    @PostMapping("/responsable")
    public ResponseEntity<?> guardarResponsable(@RequestBody ResponsableDTO responsableDTO) {
        Responsable responsable = Responsable.builder()
                .nombre(responsableDTO.getNombre())
                .apellido(responsableDTO.getApellido())
                .email(responsableDTO.getEmail())
                .telefono(responsableDTO.getTelefono())
                .direccion(responsableDTO.getDireccion())
                .sexo(responsableDTO.getSexo())
                .fechaNacimiento(responsableDTO.getFechaNacimiento())
                .dni(responsableDTO.getDni())
                .cargo(responsableDTO.getCargo())
                .build();

        responsableRepository.save(responsable);

        log.info("Responsable guardado: {}", responsable);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/responsable/eliminar/{id}")
    public void eliminarResponsable(@PathVariable Long id) {
        log.info("Responsable eliminado: {}", responsableRepository.findById(id).orElseThrow());
        responsableRepository.deleteById(id);
    }

    @PostMapping("/responsable/{id}")
    public ResponseEntity<?> actualizarResponsable(@PathVariable Long id, @RequestBody ResponsableDTO responsableDTO) {
        Responsable responsable = responsableRepository.findById(id).orElseThrow();
        responsable.setNombre(responsableDTO.getNombre());
        responsable.setApellido(responsableDTO.getApellido());
        responsable.setEmail(responsableDTO.getEmail());
        responsable.setTelefono(responsableDTO.getTelefono());
        responsable.setDireccion(responsableDTO.getDireccion());
        responsable.setSexo(responsableDTO.getSexo());
        responsable.setFechaNacimiento(responsableDTO.getFechaNacimiento());
        responsable.setDni(responsableDTO.getDni());
        responsable.setCargo(responsableDTO.getCargo());
        responsableRepository.save(responsable);
        log.info("Responsable actualizado: {}", responsableDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/responsables")
    public ResponseEntity<?> listarResponsables() {
        return ResponseEntity.ok(responsableRepository.findAll());
    }

}
