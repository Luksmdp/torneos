package com.torneos.futbol.repository;

import com.torneos.futbol.model.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipoRepository extends JpaRepository<Equipo, Integer> {
}
