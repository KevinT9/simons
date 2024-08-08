package com.dev.simons.repository;

import com.dev.simons.model.IndicadoresProyecto;
import com.dev.simons.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndicadoresProyectoRepository extends JpaRepository<IndicadoresProyecto, Long> {
    IndicadoresProyecto findByProyecto(Proyecto proyecto);
}
