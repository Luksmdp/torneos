package com.torneos.futbol.service;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.JugadorDto;
import com.torneos.futbol.model.entity.Jugador;

import java.util.List;

public interface JugadorService {
    Jugador save(JugadorDto jugadorDto) throws BadRequestException;
    void delete(Integer id) throws BadRequestException;
    Jugador findById(Integer id) throws BadRequestException;
    List<Jugador> findAll();
    Jugador update(JugadorDto jugadorDto,Integer id) throws BadRequestException;
}
