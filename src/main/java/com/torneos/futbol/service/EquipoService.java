package com.torneos.futbol.service;


import com.torneos.futbol.model.entity.Equipo;

import java.util.List;

public interface EquipoService {
    Equipo save(Equipo equipo);
    void delete(Equipo equipo);
    Equipo findById(Integer id);
    List<Equipo> findAll();
    Equipo update(Equipo equipo);
}
