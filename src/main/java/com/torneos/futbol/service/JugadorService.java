package com.torneos.futbol.service;

import com.torneos.futbol.model.entity.Jugador;

import java.util.List;

public interface JugadorService {
    Jugador save(Jugador jugador);
    void delete(Jugador jugador);
    Jugador findById(Long id);
    List<Jugador> findAll();
    void update(Jugador jugador);
}
