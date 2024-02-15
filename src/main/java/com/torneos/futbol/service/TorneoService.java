package com.torneos.futbol.service;

import com.torneos.futbol.model.dto.TorneoDto;
import com.torneos.futbol.model.entity.Jugador;
import com.torneos.futbol.model.entity.Torneo;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface TorneoService {
    Torneo save(TorneoDto torneoDto) throws BadRequestException;
    void delete(Integer id) throws BadRequestException;
    Torneo findById(Integer id) throws BadRequestException;
    List<Torneo> findAll();
    Torneo update(Torneo torneo, Integer id) throws BadRequestException;



}
