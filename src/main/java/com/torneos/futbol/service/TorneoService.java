package com.torneos.futbol.service;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.TorneoDto;
import com.torneos.futbol.model.entity.Torneo;


import java.util.List;

public interface TorneoService {
    Torneo save(TorneoDto torneoDto) throws BadRequestException;
    void delete(Integer id) throws BadRequestException;
    Torneo findById(Integer id) throws BadRequestException;
    List<Torneo> findAll();
    Torneo update(TorneoDto torneoDto, Integer id) throws BadRequestException;



}
