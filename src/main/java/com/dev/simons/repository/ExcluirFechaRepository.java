package com.dev.simons.repository;

import com.dev.simons.model.ExcluirFecha;
import com.dev.simons.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExcluirFechaRepository extends JpaRepository<ExcluirFecha, Long> {
    List<ExcluirFecha> findAllByProyecto(Proyecto proyecto);
}
