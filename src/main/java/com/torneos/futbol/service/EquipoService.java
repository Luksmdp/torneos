package com.torneos.futbol.service;


import com.torneos.futbol.model.entity.Equipo;

import java.util.List;

public interface EquipoService {
    Equipo save(Equipo equipo);
    void delete(Equipo equipo);
    Equipo findById(Long id);
    List<Equipo> findAll();
    void update(Equipo equipo);
}
