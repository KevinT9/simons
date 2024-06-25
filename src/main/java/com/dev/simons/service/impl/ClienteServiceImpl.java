package com.dev.simons.service.impl;

import com.dev.simons.model.Cliente;
import com.dev.simons.model.dto.ClienteDTO;
import com.dev.simons.repository.ClienteRepository;
import com.dev.simons.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente obtenerOcrearCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.getId() != null) {
            return clienteRepository.findById(clienteDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        } else {
            Cliente nuevoCliente = Cliente.builder()
                    .nombre(clienteDTO.getNombre())
                    .apellido(clienteDTO.getApellido())
                    .email(clienteDTO.getEmail())
                    .telefono(clienteDTO.getTelefono())
                    .direccion(clienteDTO.getDireccion())
                    .sexo(clienteDTO.getSexo())
                    .fechaNacimiento(clienteDTO.getFechaNacimiento())
                    .dni(clienteDTO.getDni())
                    .ruc(clienteDTO.getRuc())
                    .tipo(clienteDTO.getTipo())
                    .build();
            return clienteRepository.save(nuevoCliente);
        }
    }

    @Override
    public Optional<Cliente> buscarPorDni(String dni) {
        return clienteRepository.findByDni(dni);
    }

    @Override
    public Optional<Cliente> buscarPorRUC(String ruc) {
        return clienteRepository.findByRuc(ruc);
    }
}
