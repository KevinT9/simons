package com.dev.simons.rest;

import com.dev.simons.model.Cliente;
import com.dev.simons.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    private final ClienteService clienteService;

    public ClienteRestController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/dni/{dni}")
    ResponseEntity<?> buscarPorDni(@PathVariable String dni) {
        // Agregar la validación del DNI
        if (dni.length() != 8)
            return ResponseEntity.badRequest().body("El DNI debe tener 8 dígitos");

        // Agregar la validación de que el DNI sea numérico
        try {
            Long.parseLong(dni);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("El DNI debe ser numérico");
        }

        // Agregar la validación de que el DNI sea positivo
        if (Long.parseLong(dni) < 0)
            return ResponseEntity.badRequest().body("El DNI debe ser positivo");

        // Agregar la validación de que el DNI sea menor a 99999999
        if (Long.parseLong(dni) > 99999999)
            return ResponseEntity.badRequest().body("El DNI debe ser menor a 99999999");

        Optional<Cliente> cliente = clienteService.buscarPorDni(dni);

        // Agregar la validación de que el cliente exista
        if (cliente.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/ruc/{ruc}")
    ResponseEntity<?> buscarPorRUC(@PathVariable String ruc) {
        // Agregar la validación del RUC
        if (ruc.length() != 11)
            return ResponseEntity.badRequest().body("El RUC debe tener 11 dígitos");

        // Agregar la validación de que el RUC sea numérico
        try {
            Long.parseLong(ruc);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("El RUC debe ser numérico");
        }

        // Agregar la validación de que el RUC sea positivo
        if (Long.parseLong(ruc) < 0)
            return ResponseEntity.badRequest().body("El RUC debe ser positivo");

        // Agregar la validación de que el RUC sea menor a 99999999999
        if (Long.parseLong(ruc) > 99999999999L)
            return ResponseEntity.badRequest().body("El RUC debe ser menor a 99999999999");

        Optional<Cliente> cliente = clienteService.buscarPorRUC(ruc);

        // Agregar la validación de que el cliente exista y devolver un mensaje Cliente no existe
        if (cliente.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no existe");

        return ResponseEntity.ok(cliente);
    }
}
