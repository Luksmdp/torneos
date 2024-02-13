package com.torneos.futbol.service;

import com.torneos.futbol.model.entity.Jugador;
import com.torneos.futbol.model.entity.Torneo;

import java.util.List;

public interface TorneoService {
    Torneo save(Torneo torneo);
    void delete(Torneo torneo);
    Torneo findById(Integer id);
    List<Torneo> findAll();
    Torneo update(Torneo torneo);



}
