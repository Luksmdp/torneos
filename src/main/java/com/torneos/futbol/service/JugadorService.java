package com.torneos.futbol.service;

import com.torneos.futbol.model.dto.JugadorDto;
import com.torneos.futbol.model.entity.Jugador;

import java.util.List;

public interface JugadorService {
    Jugador save(JugadorDto jugadorDto);
    void delete(Jugador jugador);
    Jugador findById(Integer id);
    List<Jugador> findAll();
    Jugador update(JugadorDto jugadorDto,Integer id);
}
