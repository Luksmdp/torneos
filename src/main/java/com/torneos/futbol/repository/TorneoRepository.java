package com.torneos.futbol.repository;

import com.torneos.futbol.model.entity.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {
}
