package com.dev.simons.service;

import com.dev.simons.model.Cliente;
import com.dev.simons.model.dto.ClienteDTO;

import java.util.Optional;

public interface ClienteService {
    Cliente obtenerOcrearCliente(ClienteDTO clienteDTO);
    Optional<Cliente> buscarPorDni(String dni);
    Optional<Cliente> buscarPorRUC(String ruc);
}
