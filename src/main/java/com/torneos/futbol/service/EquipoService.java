package com.torneos.futbol.service;


import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.EquipoDto;
import com.torneos.futbol.model.entity.Equipo;

import java.util.List;

public interface EquipoService {
    Equipo save(EquipoDto equipoDto) throws BadRequestException;
    void delete(Integer id) throws BadRequestException;
    Equipo findById(Integer id) throws BadRequestException;
    List<Equipo> findAll();
    Equipo update(EquipoDto equipoDto,Integer id) throws BadRequestException;
}
